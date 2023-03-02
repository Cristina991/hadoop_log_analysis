package cn.wust.com.demo.weblog.clickstream;

import cn.wust.com.demo.mrbean.PageViewsBean;
import cn.wust.com.demo.mrbean.VisitBean;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ClickStreamVisit extends Configured implements Tool {
    @Override
    public int run(String[] strings) throws Exception {
        Configuration conf = super.getConf();
        Job job = Job.getInstance(conf);
        job.setJarByClass(ClickStreamVisit.class);

        String inputPath = "hdfs://hadoop01:9000/weblog/"+ DateUtil.getYestDate()+"/pageViewOut";
        String outputPath = "hdfs://hadoop01:9000/weblog/"+DateUtil.getYestDate()+"/visitOut";
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://hadoop01:9000"), conf,"root");
        if(fileSystem.exists(new Path(outputPath))){
            fileSystem.delete(new Path(outputPath),true);
        }
        fileSystem.close();
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.setInputPaths(job,new Path(inputPath));
        FileOutputFormat.setOutputPath(job,new Path(outputPath));


//       job.setJarByClass(ClickStreamVisit.class);
       job.setMapperClass(ClickStreamVisitMapper.class);
       job.setReducerClass(ClickStreamVisitReducer.class);
       job.setMapOutputKeyClass(Text.class);
       job.setMapOutputValueClass(PageViewsBean.class);
       job.setOutputKeyClass(NullWritable.class);
       job.setOutputValueClass(VisitBean.class);
       boolean res = job.waitForCompletion(true);
       return res?0:1;
    }

    //以session作为key，发送数据到reducer
    static class ClickStreamVisitMapper extends Mapper<LongWritable,Text,Text,PageViewsBean> {
        PageViewsBean pvBean = new PageViewsBean();
        Text k = new Text();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] fields = line.split("\001");
            int step = Integer.parseInt(fields[5]);
            pvBean.set(fields[0],fields[1],fields[2],fields[3],fields[4],step,fields[6],fields[7],fields[8],fields[9]);
            k.set(pvBean.getSession());
            context.write(k,pvBean);
        }
    }

    static  class ClickStreamVisitReducer extends Reducer<Text,PageViewsBean,NullWritable,VisitBean>{
        @Override
        protected void reduce(Text session, Iterable<PageViewsBean> pvBeans, Context context) throws IOException, InterruptedException {
            //将pvBeans按照step排序
            ArrayList<PageViewsBean> pvBeansList = new ArrayList<PageViewsBean>();
            for (PageViewsBean pageViewsBean : pvBeans) {
                PageViewsBean bean = new PageViewsBean();
                try {
                    BeanUtils.copyProperties(bean,pageViewsBean);
                    pvBeansList.add(bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            Collections.sort(pvBeansList, new Comparator<PageViewsBean>() {
                @Override
                public int compare(PageViewsBean o1, PageViewsBean o2) {
                    return o1.getStep()>o2.getStep()?1:-1;
                }
            });

            //取这次visit的首尾pageview记录，将数据放入VisitBean中
            VisitBean visitBean = new VisitBean();
            //取visit的首记录
            visitBean.setInPage(pvBeansList.get(0).getRequest());
            visitBean.setInTime(pvBeansList.get(0).getTimestr());
            //取visit的尾记录
            visitBean.setOutPage(pvBeansList.get(pvBeansList.size()-1).getRequest());
            visitBean.setOutTime(pvBeansList.get(pvBeansList.size()-1).getTimestr());
            //visit访问的页面数
            visitBean.setPageVisits(pvBeansList.size());
            //来访者的ip
            visitBean.setRemote_addr(pvBeansList.get(0).getRemote_addr());
            //本次visit的referal
            visitBean.setReferal(pvBeansList.get(0).getReferal());
            visitBean.setSession(session.toString());
            context.write(NullWritable.get(),visitBean);
        }
    }

    public static void main(String[] args) throws Exception {
        int run = ToolRunner.run(new Configuration(),new ClickStreamVisit(),args);
        System.exit(run);
    }



}
