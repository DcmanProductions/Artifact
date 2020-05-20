package com.dcman58.Handlers;

import com.dcman58.Main.GamePanel;

public class Setting {

	private String name, value;

	public Setting(String name, Object value) {
		this.name = name;
		this.value = value + "";
	}

	public Setting() {
		name = "";
		value = "";
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value + "";
		try {

			GamePanel.getInstance().settings.Save(false);
		} catch (NullPointerException e) {
			if (GamePanel.getInstance() == null) {
				Debug.Log("Instance was Null");
			} else if (GamePanel.getInstance().settings == null) {
				Debug.Log("Settings was Null");
			}
		}
	}

	public void setName(String name) {
		this.name = name;
		GamePanel.getInstance().settings.Save(false);
	}

	public boolean isEmpty() {
		return (name.isEmpty() && value.isEmpty());
	}

	public int GetInt() {
		int i = 0;
		try {
			i = Integer.parseInt(value);
		} catch (NumberFormatException e) {
			Debug.Log(value + " Is not A Number");
		}
		return i;
	}

	public float GetFloat() {
		float i = 0.f;
		try {
			i = Float.parseFloat(value);
		} catch (NumberFormatException e) {
			Debug.Log(value + " Is not A Number");
		}
		return i;
	}

	public double GetDouble() {
		double i = 0;
		try {
			i = Double.parseDouble(value);
		} catch (NumberFormatException e) {
			Debug.Log(value + " Is not A Number");
		}
		return i;
	}

	public boolean GetBoolean() {
		boolean i = false;
		try {
			i = Boolean.parseBoolean(value);
		} catch (NumberFormatException e) {
			Debug.Log(value + " Is not A Boolean");
		}
		return i;
	}

}
