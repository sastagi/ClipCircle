package com.example.santoshastagi.circleclip;

import android.net.ParseException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  NumberAnimationView mNumberAnimationView;
  Button mStartAniamtionButton;
  EditText mInputDigits;
  int numberToScrollTo;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mNumberAnimationView = (NumberAnimationView)findViewById(R.id.cvNumberAnimatedView);
    mStartAniamtionButton = (Button)findViewById(R.id.button);
    mInputDigits = (EditText) findViewById(R.id.editText);

    mNumberAnimationView.setNumberViewProperties(mNumberAnimationView.getLayoutParams().height);

    mStartAniamtionButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        boolean invalidInput = false;
        String desiredDigits = mInputDigits.getText().toString();
        try {
          numberToScrollTo = Integer.parseInt(desiredDigits);
        } catch (NumberFormatException n5te) {
          invalidInput = true;
        }

        if (invalidInput) {
          Toast.makeText(getBaseContext(), "Invalid Input", Toast.LENGTH_LONG).show();
        } else {
          mNumberAnimationView.animateNumbers(numberToScrollTo);
        }
      }
    });
  }
}
