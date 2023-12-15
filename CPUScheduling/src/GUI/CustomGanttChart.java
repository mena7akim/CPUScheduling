package GUI;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.renderer.category.GanttRenderer;
import org.jfree.data.gantt.GanttCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.*;

public class CustomGanttChart extends JPanel {
    int maxTime = 0;
    public CustomGanttChart(String title, Schedulers.Process[] processes) {
        JFreeChart chart = createChart(createDataset(processes), title);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(900, 600));
        GanttRenderer renderer = new GanttRenderer();
        chart.getCategoryPlot().setRenderer(renderer);
        for(int i = 0; i < processes.length; i++){
            renderer.setSeriesPaint(i, processes[i].getColor());
        }
        add(chartPanel);
        setVisible(true);
    }

    public GanttCategoryDataset createDataset(Schedulers.Process[] processes) {
        TaskSeriesCollection collection = new TaskSeriesCollection();
        for(Schedulers.Process process : processes){
            System.out.println(process.getName() + process.getRanges().size());
        }
        for(Schedulers.Process process : processes){
            TaskSeries taskSeries = new TaskSeries(process.getName());
            ArrayList<Schedulers.Range> ranges = process.getRanges();
            Task task = new Task(process.getName(), new SimpleTimePeriod(ranges.getFirst().getStart(), ranges.getLast().getEnd()));
            for(Schedulers.Range range : process.getRanges()){
                task.addSubtask(new Task(process.getName(), new SimpleTimePeriod(range.getStart(), range.getEnd())));
                maxTime = Math.max(maxTime, range.getEnd() + 1);
                System.out.println(range.getStart() + " " + range.getEnd());
            }
            taskSeries.add(task);
            collection.add(taskSeries);
        }
        return collection;
    }

    public JFreeChart createChart(GanttCategoryDataset dataset, String title) {
        JFreeChart chart = ChartFactory.createGanttChart(
                title, "Processes", "Time",
                dataset, true, true, false
        );
        DateAxis axis = (DateAxis) chart.getCategoryPlot().getRangeAxis();
        axis.setTickUnit(new DateTickUnit(DateTickUnit.MILLISECOND, 1));
        axis.setMaximumDate(new Date(maxTime));
        axis.setDateFormatOverride(new SimpleDateFormat("S"));
        return chart;
    }
}
