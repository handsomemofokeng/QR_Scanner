package za.co.britehouse.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void scan() {
		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.setCaptureActivity(Portrait.class);
		integrator.setOrientationLocked(false);
		integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
		integrator.setPrompt("Scan your barcode...");
		integrator.initiateScan();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
		if (result != null) {
			if (result.getContents() == null) {
				Toast.makeText(this, getString(R.string.not_found), Toast.LENGTH_SHORT).show();
			} else {
				showScanDialog(result);
			}
		} else {
			super.onActivityResult(requestCode, resultCode, data);
		}
	}

	private void showScanDialog(IntentResult result) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(result.getContents() + "\n\n Scan Again?");
		builder.setTitle("Scanned Result: ");

		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				scan();
			}
		});

		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		});
		AlertDialog alertDialog = builder.create();
		alertDialog.show();
	}

	public void onClick_Scan(View view) {
		scan();
	}
}