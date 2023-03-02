package cn.wust.com.demo.pre;

import cn.wust.com.demo.mrbean.WebLogBean;
import cn.wust.com.demo.utils.DateUtil;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

/***
 * 处理原始日志，过滤出真实pv请求 转换时间格式 对缺失字段填充默认值，对记录标记Valid和invalidate
 */
public class WeblogPreProcess extends Configured implements Tool {
    @Override
    public int run(String[] strings) throws Exception {
        Configuration conf = super.getConf();
        Job job = Job.getInstance(conf);
        job.setJarByClass(WeblogPreProcess.class);

        String inputPath = "hdfs://hadoop01:9000/weblog/" + DateUtil.getYestDate() + "/input";
        String outputPath = "hdfs://hadoop01:9000/weblog/" + DateUtil.getYestDate() + "/weblogPreOut";
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://hadoop01:9000"), conf,"root");
//        fileSystem.mkdirs(new Path("/weblog/"+DateUtil.getYestDate() + "/input"));

//        fileSystem.copyFromLocalFile(new Path("F:\\学习资料\\毕业设计\\data\\input\\access.log.fensi"), new Path("/weblog/"+DateUtil.getYestDate()+ "/input" ));

        if (fileSystem.exists(new Path(outputPath))) {
           fileSystem.delete(new Path(outputPath), true);
        }
        fileSystem.close();

        //输入
        job.setInputFormatClass(TextInputFormat.class);
        FileInputFormat.setInputPaths(job, new Path(inputPath));

        job.setMapperClass(weblogPreProcessMapper.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);  //这里没有预处理

        //输出
        job.setOutputFormatClass(TextOutputFormat.class);
        FileOutputFormat.setOutputPath(job, new Path(outputPath));

        boolean res = job.waitForCompletion(true);
        return res ? 0 : 1;
    }

    //静态内部类<k1,v1,k2,v2>
    static class weblogPreProcessMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
        //用来存储网站url分类数据
        Set<String> pages = new HashSet<>();
        Text k = new Text();
        NullWritable v = NullWritable.get();

        /***
         * 从外部配置文件中加载网站的有用url分类数据 存储到maptask的内存中，用来对日志数据进行过滤
         */
        //第一次执行MapReduce的时候它会执行 就是一个初始化 没有意义的路径
        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            pages.add("/about");
            pages.add("/black-ip-list/");
            pages.add("/cassandra-clustor/");
            pages.add("/finance-rhive-repurchase/");
            pages.add("/hadoop-family-roadmap/");
            pages.add("/hadoop-hive-intro/");
            pages.add("/hadoop-zookeeper-intro/");
            pages.add("/hadoop-mahout-roadmap/");

        }

        //k1,v1 zhuancheng k2,v2
        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            WebLogBean webLogBean = WeblogParser.parser(line);
            if (webLogBean != null) {
                //过滤js/图片/css等静态资源
                WeblogParser.filleStaticResource(webLogBean, pages);
//                if(!webLogBean.isvalid()) return ;
                k.set(webLogBean.toString());
//                System.out.println(webLogBean.toString());
                context.write(k, v);
            }
        }
    }

        public static void main(String[] args) throws Exception {
            Configuration configuration = new Configuration();
            int run = ToolRunner.run(configuration, new WeblogPreProcess(), args);
            System.exit(run);
        }

}
