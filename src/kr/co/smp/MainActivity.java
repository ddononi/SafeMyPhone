package kr.co.smp;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * 메인 엑티비티로 중력센서 리스너를 통해 기울기 정도를 감지한다. 
 * @author ddononi_nb
 *
 */
public class MainActivity extends Activity implements SensorEventListener {
	private Context mContext;
	// hardware
	private SensorManager mSensorManager;
	private Sensor accelerateSenser;
	private float mX;	//  기울기 x값	
	// effect
	private SoundPool soundPool;
	private Vibrator vibe;		
	private int currectBeep;	
	private boolean isAlarmed = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        init();
    }

    /**
     * 하드웨어 자원 얻기 및 사운드풀 설정
     */
    private void init() {
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		accelerateSenser = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		soundPool = new SoundPool( 5, AudioManager.STREAM_MUSIC, 0 );
		currectBeep = soundPool.load(this, R.raw.s, 1 );		
		
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);				
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
	
	@Override
	protected void onResume() {
		super.onResume();
		if (accelerateSenser != null) {
			mSensorManager.registerListener(this, accelerateSenser,
					SensorManager.SENSOR_DELAY_GAME);
		}

	}	
	
	@Override
	protected void onStop() {
		super.onStop();
		soundPool.stop(currectBeep);
		mSensorManager.unregisterListener(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	private int aa = 0;
	/**
	 * 센서 변경 감지
	 * @param event
	 */
	@Override
	public void onSensorChanged(SensorEvent event) {
		if (event.sensor == accelerateSenser) {
			if(aa % 40 == 0){
				mX = Math.abs(Math.round(event.values[1] * 10));
				if(mX > 10){
					//Toast.makeText(mContext, "mX :: "  + mX, Toast.LENGTH_SHORT).show();'
					if(isAlarmed == false){
						isAlarmed = true;
						soundPool.play(currectBeep, 1f, 1f, 0, -1, 1f );
					}
				}else{
					//isAlarmed = false;
				}
				Log.i("tag", "mX ::: "  + mX);
				aa = 0;
			}
			
			aa++;
		}
	}	

    
}
