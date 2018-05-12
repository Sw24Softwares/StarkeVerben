package org.sw24softwares.starkeverben;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

public class XAxisValueFormatter implements IAxisValueFormatter {
    private String[] mValues;

    public XAxisValueFormatter(String[] dates) {
        mValues = dates;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        if(mValues.length > (int) value && (int) value >= 0)
            return mValues[(int) value];
        return "";
    }
}
