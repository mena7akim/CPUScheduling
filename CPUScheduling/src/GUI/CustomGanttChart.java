package GUI;

import org.jfree.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.gantt.GanttCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import java.text.SimpleDateFormat;

public class CustomGanttChart extends ApplicationFrame {
    public CustomGanttChart(String title) {
        super(title);
        JFreeChart chart = createChart(createDataset(), title);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1000, 600));
        setContentPane(chartPanel);
        this.setVisible(true);
    }

    public GanttCategoryDataset createDataset() {
        return null;
    }

    public JFreeChart createChart(GanttCategoryDataset dataset, String title) {
        JFreeChart chart = ChartFactory.createGanttChart(
                title, "Processes", "Time",
                dataset, true, true, false
        );
        DateAxis axis = (DateAxis) chart.getCategoryPlot().getRangeAxis();
        axis.setTickUnit(new DateTickUnit(DateTickUnit.MILLISECOND, 5));
        axis.setDateFormatOverride(new SimpleDateFormat("ss"));
        return chart;
    }
}
