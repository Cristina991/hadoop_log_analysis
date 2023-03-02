package cn.wust.com.demo.pre;

import cn.wust.com.demo.mrbean.UserBehaviorBean;
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
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserBehaviorPreProcess extends Configured implements Tool {
    @Override
    public int run(String[] strings) throws Exception {
        Configuration conf = super.getConf();
        Job job = Job.getInstance(conf);
        job.setJarByClass(WeblogPreProcess.class);

        String inputPath = "hdfs://hadoop01:9000/weblog/" + DateUtil.getYestDate() + "/input/userbehavior";
        String outputPath = "hdfs://hadoop01:9000/weblog/" + DateUtil.getYestDate() + "/userbehaviorPreOut";
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://hadoop01:9000"), conf,"root");

        if (fileSystem.exists(new Path(outputPath))) {
            fileSystem.delete(new Path(outputPath), true);
        }
        fileSystem.close();

        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);
        FileInputFormat.setInputPaths(job,new Path(inputPath));
        FileOutputFormat.setOutputPath(job,new Path(outputPath));

        job.setMapperClass(UserBehaviorPreProcess.userbehaviorPreProcessMapper.class);
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        boolean res = job.waitForCompletion(true);
        return res ? 0 : 1;
    }
    //静态内部类<k1,v1,k2,v2>
    static class userbehaviorPreProcessMapper extends Mapper<LongWritable, Text, Text,NullWritable> {
        Text k = new Text();
        NullWritable val = NullWritable.get();
        UserBehaviorBean v = new UserBehaviorBean();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String line = value.toString();
            String[] fields = line.split(",");
            if(fields.length<5) return;

            String time;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lt = new Long(fields[4]);
            Date date = new Date(lt*1000);
            time = simpleDateFormat.format(date);
            String date_ = time.substring(0,10);
            String hour = time.substring(11,13);


            try {
                DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
                Date date1 = dateformat.parse("2017-12-03");
                Date date2 = dateformat.parse("2017-11-25");
                Date d = dateformat.parse(date_);
                if(d.compareTo(date1)>0||d.compareTo(date2)<0)
                    return;

            } catch (ParseException e) {
                e.printStackTrace();
            }

            v.set(fields[0],fields[1],fields[2],fields[3],fields[4],time,date_,hour);
            k.set(v.toString());
            context.write(k,val);

        }

    }


    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        int run = ToolRunner.run(configuration, new UserBehaviorPreProcess(), args);
        System.exit(run);
    }
}
