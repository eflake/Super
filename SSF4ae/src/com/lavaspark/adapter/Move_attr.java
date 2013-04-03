package com.lavaspark.adapter;

public class Move_attr {
	String[] person_moves = new String[]{"Block","Damage","Stun","MeterGain","CancelAbility"
			,"Startup","Active","Recover","FrameAdvBlock","FrameAdvHit","Notes"};
	private String mMove_Name ;
	public String getmMove_Name() {
		return mMove_Name;
	}
	public void setmMove_Name(String mMove_Name) {
		this.mMove_Name = mMove_Name;
	}
	public String getmStartup() {
		return mStartup;
	}
	public void setmStartup(String mStartup) {
		this.mStartup = mStartup;
	}
	public String getmOnGruard() {
		return mOnGruard;
	}
	public void setmOnGruard(String mOnGruard) {
		this.mOnGruard = mOnGruard;
	}
	public String getmOnHit() {
		return mOnHit;
	}
	public void setmOnHit(String mOnHit) {
		this.mOnHit = mOnHit;
	}
	
	public Move_attr() {
		super();
	}

	private String mStartup ;
	private String mOnGruard ;
	private String mOnHit ;
	private String mHL ;
	private String mDamage ;
	private String mStun ;
	private String mGain ;
	private String mCancelAbility ;
	private String mActive ;
	private String mRecovery ;
	private String mNotes ;
	public String getmHL() {
		return mHL;
	}
	public void setmHL(String mHL) {
		this.mHL = mHL;
	}
	public String getmDamage() {
		return mDamage;
	}
	public void setmDamage(String mDamage) {
		this.mDamage = mDamage;
	}
	public String getmStun() {
		return mStun;
	}
	public void setmStun(String mStun) {
		this.mStun = mStun;
	}
	public String getmGain() {
		return mGain;
	}
	public void setmGain(String mGain) {
		this.mGain = mGain;
	}
	public String getmCancelAbility() {
		return mCancelAbility;
	}
	public void setmCancelAbility(String mCancelAbility) {
		this.mCancelAbility = mCancelAbility;
	}
	public String getmActive() {
		return mActive;
	}
	public void setmActive(String mActive) {
		this.mActive = mActive;
	}
	public String getmRecovery() {
		return mRecovery;
	}
	public void setmRecovery(String mRecovery) {
		this.mRecovery = mRecovery;
	}
	public String getmNotes() {
		return mNotes;
	}
	public void setmNotes(String mNotes) {
		this.mNotes = mNotes;
	}
	public Move_attr(String mMove_Name, String mStartup, String mOnGruard,
			String mOnHit, String mHL, String mDamage, String mStun,
			String mGain, String mCancelAbility, String mActive,
			String mRecovery, String mNotes) {
		super();
		this.mMove_Name = mMove_Name;
		this.mStartup = mStartup;
		this.mOnGruard = mOnGruard;
		this.mOnHit = mOnHit;
		this.mHL = mHL;
		this.mDamage = mDamage;
		this.mStun = mStun;
		this.mGain = mGain;
		this.mCancelAbility = mCancelAbility;
		this.mActive = mActive;
		this.mRecovery = mRecovery;
		this.mNotes = mNotes;
	}
	public Move_attr(String mMove_Name, String mStartup, String mOnGruard,
			String mOnHit) {
		super();
		this.mMove_Name = mMove_Name;
		this.mStartup = mStartup;
		this.mOnGruard = mOnGruard;
		this.mOnHit = mOnHit;
	}



}
