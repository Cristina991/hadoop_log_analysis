package cn.wust.com.demo.weblog.clickstream;

import cn.wust.com.demo.mrbean.WebLogBean;
import cn.wust.com.demo.utils.DateUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ClickStreamPageView extends Configured implements Tool {
    @Override
    public int run(String[] strings) throws Exception {
        Configuration conf = super.getConf();
        Job job = Job.getInstance(conf);
        job.setJarByClass(ClickStreamPageView.class);

        String inputPath = "hdfs://hadoop01:9000/weblog/"+ DateUtil.getYestDate()+"/weblogPreOut";
        String outputPath = "hdfs://hadoop01:9000/weblog/"+DateUtil.getYestDate()+"/pageViewOut";
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://hadoop01:9000"), conf,"root");
        if(fileSystem.exists(new Path(outputPath))){
            fileSystem.delete(new Path(outputPath),true);
        }
        fileSystem.close();
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.setInputPaths(job,new Path(inputPath));
        FileOutputFormat.setOutputPath(job,new Path(outputPath));

        job.setMapperClass(ClickStreamMapper.class);
        job.setReducerClass(ClickStreamReducer.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(WebLogBean.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        boolean b = job.waitForCompletion(true);
        return b?0:1;
    }

    static class ClickStreamMapper extends Mapper<LongWritable,Text,Text,WebLogBean>{
        Text k = new Text();
        WebLogBean v = new WebLogBean();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] fields = line.split("\001");
            if(fields.length<9) return;
            //将切分出来的各字段set到weblogbean中
            v.set("true".equals(fields[0])?true:false,fields[1],fields[2],fields[3],fields[4],fields[5],fields[6],fields[7],fields[8]);
            //只有有效记录才进入后续处理
            if(v.isValid()){
                //此处用ip地址来标记用户
                k.set(v.getRemote_addr());
                context.write(k,v);
            }
        }
    }

    static class ClickStreamReducer extends Reducer<Text,WebLogBean, NullWritable,Text>{
        Text v = new Text();
        /*
            192.168.174.32  </w>www.baidu.com 12:00, /www.baidu.com 12.20
         */
        //key:ip
        @Override
        protected void reduce(Text key, Iterable<WebLogBean> values, Context context) throws IOException, InterruptedException {
            ArrayList<WebLogBean> beans = new ArrayList<WebLogBean>();
            //先将一个用户的所有访问记录中的时间拿出来排序
            for (WebLogBean bean : values) {
                //为什么List集合当中不能直接添加循环出来的这个bean？
                //这里通过属性拷贝，每次new一个对象，避免了bean的属性值每次覆盖
                WebLogBean webLogBean = new WebLogBean();
                try {
                    BeanUtils.copyProperties(webLogBean, bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                beans.add(webLogBean);
            }
            //将bean按时间先后顺序排序
            Collections.sort(beans, new Comparator<WebLogBean>() {
                @Override
                public int compare(WebLogBean o1, WebLogBean o2) {

                    try {
                        Date d1 = toDate(o1.getTime_local());
                        Date d2 = toDate(o2.getTime_local());
                        if (d1 == null || d2 == null)
                            return 0;
                        return d1.compareTo(d2);  //2018-12-23 12:32:46  -->30053835859898
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return 0;
                    }


                }
            });

            /***
             * 以下逻辑为：从有序bean中分辨出各次visit，并对一次visit中所访问的page按顺序标号step
             * 核心思想：
             * 就是比较相邻两条记录中的时间差，如果时间差<30min，则该两条记录属于同一个session
             * 否则，就属于不同的session
             */
            int step = 1;
            String session = UUID.randomUUID().toString();
            for (int i = 0; i < beans.size(); i++) {
                WebLogBean bean = beans.get(i);
                //如果仅有3条数据，则直接输出
                if (1 == beans.size()) {
                    //设置默认停留时长为60s
                    v.set(session + "\001" + key.toString() + "\001" + bean.getRemote_user() + "\001" + bean.getTime_local()
                            + "\001" + bean.getRequest() + "\001" + step + "\001" + (60) + "\001" + bean.getHttp_referer() + "\001"
                            + bean.getHttp_user_agent() + "\001" + bean.getBody_bytes_sent() + "\001" + bean.getStatus());
                    context.write(NullWritable.get(), v);
                    session = UUID.randomUUID().toString();
                    break;
                }
                //如果不止一条数据，则将第一条数据跳过不输出，遍历第二条时再输出
                if (i == 0) {
                    continue;
                }
                //求近两次时间差
                long timeDiff = 0;
                try {
                    timeDiff = timeDiff(toDate(bean.getTime_local()), toDate(beans.get(i - 1).getTime_local()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //如果本次-上次时间差<30min，则输出前一次的页面访问信息
                if (timeDiff < 30 * 60 * 1000) {
                    v.set(session + "\001" + key.toString() + "\001" + beans.get(i - 1).getRemote_user() + "\001" + beans.get(i - 1).getTime_local() + "\001"
                            + beans.get(i - 1).getRequest() + "\001" + step + "\001" + (timeDiff / 1000) + "\001" + beans.get(i - 1).getHttp_referer() + "\001"
                            + beans.get(i - 1).getHttp_user_agent() + "\001" + beans.get(i - 1).getBody_bytes_sent() + "\001"
                            + beans.get(i - 1).getStatus());
                    context.write(NullWritable.get(), v);
                    step++;
                } else {
                    //如果本次-上次时间差>30min，则输出前一次的页面访问信息且将step重置，以分隔为新的visit
                    v.set(session + "\001" + key.toString() + "\001" + beans.get(i - 1).getRemote_user() + "\001" + beans.get(i - 1).getTime_local() + "\001"
                            + beans.get(i - 1).getRequest() + "\001" + step + "\001" + (60) + "\001" + beans.get(i - 1).getHttp_referer() + "\001"
                            + beans.get(i - 1).getHttp_user_agent() + "\001" + beans.get(i - 1).getBody_bytes_sent() + "\001"
                            + beans.get(i - 1).getStatus());
                    context.write(NullWritable.get(), v);
                    //输出完上一条之后，重置step编写
                    step = 1;
                    session = UUID.randomUUID().toString();
                }

                //如果此次遍历的是最后一条，则将本条直接输出
                if (i == beans.size() - 1) {
                    //设置默认停留时长为60s
                    v.set(session + "\001" + key.toString() + "\001" + bean.getRemote_user() + "\001" + bean.getTime_local()
                            + "\001" + bean.getRequest() + "\001" + step + "\001" + (60) + "\001" + bean.getHttp_referer() + "\001"
                            + bean.getHttp_user_agent() + "\001" + bean.getBody_bytes_sent() + "\001" + bean.getStatus());

                }
            }

        }
    }

    private String toStr(Date date){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return df.format(date);
    }

    private static Date toDate(String timeStr) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        return df.parse(timeStr);
    }

    private long timeDiff(String time1,String time2) throws ParseException {
        Date d1 = toDate(time1);
        Date d2 = toDate(time2);
        return d1.getTime()-d2.getTime();
    }

    private static long timeDiff(Date time1, Date time2){
        return time1.getTime()-time2.getTime();
    }

    public static void main(String[] args) throws Exception {
        int run = ToolRunner.run(new Configuration(),new ClickStreamPageView(),args);
        System.exit(run);
    }
}
