package com.vs.tasks.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import com.vs.tasks.R;

public class WidgetPoints extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
//            Intent intent = new Intent(context, MainActivity.class);
//            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            SharedPreferences points = context.getSharedPreferences("points", Context.MODE_PRIVATE);
            int score = points.getInt("points", 0);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_points);
            String pointsText = "Points : " + score;
            views.setTextViewText(R.id.points, pointsText);
            double level = checkLevel(score);
            double progress = score / (50 * level) * 100;
            views.setProgressBar(R.id.progressBar, 100, (int) progress, false);
            views.setImageViewResource(R.id.imageView, setLevelPicture(checkLevel(score)));
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    private int checkLevel(int x) {
        if (x < 50) {
            return 1;
        } else if (x < 100) {
            return 2;
        } else if (x < 150) {
            return 3;
        } else if (x < 200) {
            return 4;
        } else if (x < 250) {
            return 5;
        } else if (x < 300) {
            return 6;
        } else if (x < 350) {
            return 7;
        } else if (x < 400) {
            return 8;
        } else if (x < 450) {
            return 9;
        } else if (x < 500) {
            return 10;
        } else if (x < 550) {
            return 11;
        } else if (x < 600) {
            return 12;
        } else if (x < 650) {
            return 13;
        } else {
            return 13;
        }
    }

    private int setLevelPicture(int x) {
        switch (x) {
            case 1:
                return R.drawable.l1;
            case 2:
                return R.drawable.l2;
            case 3:
                return R.drawable.l3;
            case 4:
                return R.drawable.l4;
            case 5:
                return R.drawable.l5;
            case 6:
                return R.drawable.l6;
            case 7:
                return R.drawable.l7;
            case 8:
                return R.drawable.l8;
            case 9:
                return R.drawable.l9;
            case 10:
                return R.drawable.l10;
            case 11:
                return R.drawable.l11;
            case 12:
                return R.drawable.l12;
            case 13:
                return R.drawable.l13;
        }
        return R.drawable.l1;
    }
}