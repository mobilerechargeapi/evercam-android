package io.evercam.androidapp;

import io.evercam.androidapp.custom.CustomToast;
import io.evercam.androidapp.email.FeedbackSender;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class FeedbackActivity extends Activity 
{
	private final String TAG = "evercamplay-FeedbackActivity";
	private EditText feedbackEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		if (this.getActionBar() != null)
		{
			this.getActionBar().setDisplayHomeAsUpEnabled(true);
			this.getActionBar().setIcon(R.drawable.icon_50x50);
		}
		
		setContentView(R.layout.activity_feedback);
		
		feedbackEditText = (EditText) findViewById(R.id.feedback_edit_text);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.menu_feedback, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{

		int id = item.getItemId();
		if (id == R.id.action_send) 
		{
			final String feedbackString = feedbackEditText.getText().toString();
			if(feedbackString.isEmpty())
			{
				//Do nothing
			}
			else
			{
				feedbackEditText.setText("");
				CustomToast.showInCenter(this, R.string.msg_feedback_sent);
				new Thread(new Runnable()
				{
					@Override
					public void run() 
					{
						FeedbackSender feedbackSender = new FeedbackSender(FeedbackActivity.this);
						feedbackSender.send(feedbackString);
					}
				}).start();
				finish();
			}
			
			return true;
		}
		else if(id == android.R.id.home)
		{
			this.finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void sendFeedback()
	{
		String feedbackString = feedbackEditText.getText().toString();
		if(feedbackString.isEmpty())
		{
			//Do nothing
		}
		else
		{
			CustomToast.showInCenter(this, R.string.msg_feedback_sent);
			FeedbackSender feedbackSender = new FeedbackSender(this);
			feedbackSender.send(feedbackString);
		}
	}
}
