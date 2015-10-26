package com.bluebird_tech.puffin;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.renderer.YAxisRenderer;
import com.github.mikephil.charting.utils.Transformer;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.j256.ormlite.android.compat.ApiCompatibilityUtils;

public class ColorYAxisRenderer extends YAxisRenderer {
  private final Context context;
  private int[] gridColors;

  public ColorYAxisRenderer(Context ctx, ViewPortHandler viewPortHandler, YAxis yAxis, Transformer trans) {
    super(viewPortHandler, yAxis, trans);
    this.context = ctx;
  }

  @Override
  public void renderGridLines(Canvas c) {
    if (!mYAxis.isDrawGridLinesEnabled() || !mYAxis.isEnabled())
      return;

    // pre alloc
    float[] position = new float[2];

    mGridPaint.setColor(mYAxis.getGridColor());
    mGridPaint.setStrokeWidth(mYAxis.getGridLineWidth());
    mGridPaint.setPathEffect(mYAxis.getGridDashPathEffect());

    Path gridLinePath = new Path();

    // draw the horizontal grid
    for (int i = 0; i < mYAxis.mEntryCount; i++) {
      if ((gridColors != null) && (gridColors.length > 0)) {
        int color = gridColors[i % gridColors.length];
        mGridPaint.setColor(ContextCompat.getColor(context, color));
      }

      position[1] = mYAxis.mEntries[i];
      mTrans.pointValuesToPixel(position);

      gridLinePath.moveTo(mViewPortHandler.offsetLeft(), position[1]);
      gridLinePath.lineTo(mViewPortHandler.contentRight(), position[1]);

      // draw a path because lines don't support dashing on lower android versions
      c.drawPath(gridLinePath, mGridPaint);

      gridLinePath.reset();
    }
  }

  public int[] getGridColors() {
    return gridColors;
  }

  public void setGridColors(int[] gridColors) {
    this.gridColors = gridColors;
  }
}
