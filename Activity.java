public class Activity extends ContextThemeWrapper implements LayoutInflater.Factory2, Window.Callback, KeyEvent.Callback, 
      							                             OnCreateContextMenuListener, ComponentCallbacks2 
{
    private Application mApplication;
    /*package*/ Intent mIntent;
    Activity mParent;

    /*package*/ boolean mResumed;
    private boolean mStopped;
    boolean mFinished;
    boolean mStartedActivity;
    private boolean mDestroyed;

    private SearchManager mSearchManager;
    private MenuInflater mMenuInflater;

    private Window mWindow;
    private WindowManager mWindowManager;

    /*package*/ View mDecor = null;
    
    private Thread mUiThread;
    final Handler mHandler = new Handler();
    
    protected void onCreate(Bundle savedInstanceState) 
    {
	

    }




}
