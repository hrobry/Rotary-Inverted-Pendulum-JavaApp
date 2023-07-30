import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

/**
 * @see //https://stackoverflow.com/a/21307289/230513
 */


public class scope extends JPanel implements Runnable {

    private final DynamicTimeSeriesCollection dataset;
    private final JFreeChart chart;
    private String title;
    private String xtitle;
    private String ytitle;
    private String rotaryLabel = "Torque";
    private String pendulumPlacementLabel = "Pendulum Placement";
    public static int rotaryData;
    public static int pendulumData;
    public static String StringData;


    public scope(final String title ,final String xTitle,final String yTitle, String StringData) {
        this.StringData = StringData;
        this.title = title;
        this.xtitle = xTitle;
        this.ytitle = yTitle;

        dataset = new DynamicTimeSeriesCollection(2, 500, new Second());
        dataset.setTimeBase(new Second(0, 0, 0, 23, 1, 2014));
        dataset.addSeries(new float[1], 0, rotaryLabel);
        dataset.addSeries(new float[1],1,pendulumPlacementLabel);
        chart = ChartFactory.createTimeSeriesChart(
                title, xTitle, yTitle, dataset, true, true, false);
        final XYPlot plot = chart.getXYPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setFixedAutoRange(1000000);
        axis.setDateFormatOverride(new SimpleDateFormat("ss.SS"));
        final ChartPanel chartPanel = new ChartPanel(chart);
        add(chartPanel);
    }

    public void update(float value,float value2) {
        float[] newData = new float[2];
        newData[0] = value;
        newData[1] = value2;
        dataset.advanceTime();
        dataset.appendData(newData);
    }

    public static void main(String[] args) { // Testing
        EventQueue.invokeLater(new Runnable() {


            public void run() {
                    JFrame frame = new JFrame("testing");
                    frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    final scope chart
                            = new scope("Alternating data","Time" ,"Torque & pendulum placement", StringData);
                    frame.add(chart);
                    frame.pack();
                    Timer timer = new Timer(1000, new ActionListener() {
                        private boolean b;
                        public void actionPerformed(ActionEvent e) {
                            chart.update(11,15);
                            b = !b;
                        }
                    });
                    timer.start();
                    frame.setVisible(true);
            }
        });
    }

    public void run() {

            JFrame frame = new JFrame("Rotary pendulum scope");
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.setBounds(300, 100, 300, 300);
            final scope chart= new scope(title,xtitle,ytitle, StringData);
            frame.add(chart);
            frame.pack();
            int rotaryTemportary , pendulumTemportary;
            frame.setVisible(true);
            while (true) {
                try {
                    pendulumTemportary = Integer.valueOf(toByte.MotorPWM);
                    rotaryTemportary= Integer.valueOf(toByte.Torque);
                    rotaryData = rotaryTemportary/10000000;
                    pendulumData =pendulumTemportary/1000000;
                    chart.update(rotaryTemportary,pendulumTemportary);
                }catch (NumberFormatException g){
                    System.out.println("error");
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                }
            }
    }
}