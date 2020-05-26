package ro.disertatie.cleanbud.View.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import ro.disertatie.cleanbud.R;
import ro.disertatie.cleanbud.View.Models.Converters.TimestampConverter;
import ro.disertatie.cleanbud.View.Models.Expense;
import ro.disertatie.cleanbud.View.Models.Income;
import ro.disertatie.cleanbud.View.Utils.RecordProtocol;

public class RecordBudgetAdapter extends BaseAdapter {
    private final Activity context;

    private List<RecordProtocol> protocols;
    @SuppressLint("SimpleDateFormat")
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd, MMM, yyyy");


    public RecordBudgetAdapter(Activity context, List<RecordProtocol> protocols) {

        this.context = context;
        this.protocols = protocols;
        Collections.shuffle(this.protocols);
    }


    @Override
    public int getCount() {
        return protocols.size();
    }

    @Override
    public Object getItem(int position) {
        return protocols.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.row_record, null, true);

        TextView firstLetter = rowView.findViewById(R.id.tv_char_record);
        TextView titleRecord = rowView.findViewById(R.id.tv_title_record);
        TextView dateRecord = rowView.findViewById(R.id.tv_date_record);
        TextView sumRecord = rowView.findViewById(R.id.tv_sum_record);

        Drawable background = firstLetter.getBackground();

            Random random = new Random();

        background.setColorFilter(new PorterDuffColorFilter(Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256)), PorterDuff.Mode.LIGHTEN));

        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            firstLetter.setBackgroundDrawable(background);
        } else {
            firstLetter.setBackground(background);
        }


        if(protocols.get(position) instanceof Expense) {
            Expense expense = (Expense) protocols.get(position);
            firstLetter.setText(String.valueOf(expense.getTitleExpense().charAt(0)));
            titleRecord.setText(expense.getTitleExpense());
            dateRecord.setText(simpleDateFormat.format(expense.getExpenseDate()));
            //tipul monedei trb adaugat in bd
            sumRecord.setText("-Lei "+String.format("%.2f",expense.getAmountExpense()));


        }else if(protocols.get(position) instanceof Income) {
            Income expense = (Income) protocols.get(position);

            firstLetter.setText(String.valueOf(expense.getTitleIncome().charAt(0)));
            titleRecord.setText(expense.getTitleIncome());

            dateRecord.setText(simpleDateFormat.format(expense.getDateIncome()));
            //tipul monedei trb adaugat in bd
            sumRecord.setText("+Lei "+String.format("%.2f",expense.getAmountIncome()));
        }



        return rowView;
    }


}
