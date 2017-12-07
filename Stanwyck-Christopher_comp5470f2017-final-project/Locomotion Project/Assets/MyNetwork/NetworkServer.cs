using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Threading;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System;


public class NetworkServer : MonoBehaviour {
	static UdpClient udp;
	Thread thread;
	string returnData = "";
	//UdpClient udp;
	IPEndPoint RemoteIpEndPoint;
	static public long timestampPhoneSensor;
	static public float xPhoneSensor=0.0f;
	static public float yPhoneSensor=0.0f;
	static public float zPhoneSensor=0.0f;

	// Use this for initialization
	void Start () {
		Debug.Log ("Starting Network script");
		//socket recvSock = new DatagramSocket;
		udp = new UdpClient(51000);
		RemoteIpEndPoint = new IPEndPoint (IPAddress.Any, 0);

		thread = new Thread(new ThreadStart(ThreadMethod));
		thread.Start ();
	}
	
	// Update is called once per frame
	void Update () {
	//	thread = new Thread(new ThreadStart(ThreadMethod));
	//	thread.Start (); 
	}

	private void ThreadMethod()
	{
		long timestamp;
		float x;
		float y;
		float z;

	//	udp = new UdpClient(51000);
	//	IPEndPoint RemoteIpEndPoint = new IPEndPoint (IPAddress.Any, 0);
		while (true) {
		//	IPEndPoint RemoteIpEndPoint = new IPEndPoint (IPAddress.Any, 0);

			try{
			 byte[] receiveBytes = udp.Receive (ref RemoteIpEndPoint);
	
				timestamp = ((long)receiveBytes[7] & 0xff)
					| (((long)receiveBytes[6] & 0xff) << 8)
					| (((long)receiveBytes[5] & 0xff) << 16) 
					| (((long)receiveBytes[4] & 0xff) << 24) 
					| (((long)receiveBytes[3] & 0xff) << 32)
					| (((long)receiveBytes[2] & 0xff) << 40)
					| (((long)receiveBytes[1] & 0xff) << 48)
					| (((long)receiveBytes[0] & 0xff) << 56);

				byte[] tempBytes = new byte[4];

				tempBytes[3] = receiveBytes[8];
				tempBytes[2] = receiveBytes[9];
				tempBytes[1] = receiveBytes[10];
				tempBytes[0] = receiveBytes[11];


				x = System.BitConverter.ToSingle(tempBytes, 0);

			Debug.Log ("Received packet");
			//Debug.Log("Timestamp len " timestamp.l
			//	Debug.Log ("Timestamp: " +timestamp.ToString());
			//	Debug.Log ("X: " + x.ToString());
				tempBytes[3] = receiveBytes[12];
				tempBytes[2] = receiveBytes[13];
				tempBytes[1] = receiveBytes[14];
				tempBytes[0] = receiveBytes[15];
				y = System.BitConverter.ToSingle(tempBytes, 0);
			//	Debug.Log("Y: " + y);
				tempBytes[3] = receiveBytes[16];
				tempBytes[2] = receiveBytes[17];
				tempBytes[1] = receiveBytes[18];
				tempBytes[0] = receiveBytes[19];
				z = System.BitConverter.ToSingle(tempBytes, 0);
			//	Debug.Log("Z: " + z);

			}
			catch(ThreadAbortException) {
				Debug.Log ("Stopping UDP receive");
				break;
			}
			catch (Exception e) {
				Debug.Log ("UDP Receive exception" + e.ToString ());

				break;
			}

			timestampPhoneSensor = timestamp;
			xPhoneSensor = x;
			yPhoneSensor = y;
			zPhoneSensor = z;

		}

	}
	void OnApplicationQuit()
	{
		Debug.Log ("Stopping network thread");
		udp.Close ();
		Debug.Log ("Closed udp");
		thread.Abort ();
	}
}
