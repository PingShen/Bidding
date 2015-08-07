package com.huangyezhaobiao.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import org.apache.http.util.EncodingUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class FileUtils {
	private static volatile File file;
	private static final String file_path = "/newfile.txt";
	private static void showAvailableBytes(InputStream in) {
		try {
			System.out.println("当前字节输入流中的字节数为:" + in.available());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static synchronized void write(Context context,String json) {
		String space = System.getProperty("line.separator");
		json = json + space;
		FileOutputStream fop = null;
		OutputStreamWriter writer = null;
	//	String content = "This is the text content";
		try {
			String path = context.getFilesDir().getPath();
			Log.e("ashenhjhj", "path:"+path);
			String paths = path + file_path;
			file = new File(paths);
			fop = new FileOutputStream(file,true);
			 writer = new OutputStreamWriter(fop,"utf-8");
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			// get the content in bytes
			byte[] contentInBytes = json.getBytes();
			fop.write(contentInBytes);
		//	writer.write(json);
			fop.flush();
		//	writer.flush();
			fop.close();
			//writer.close();
			 //Log.e("ashenqqq", "done:"+json);

		} catch (IOException e) {
			e.printStackTrace();
			 Log.e("ashenFile", "error write");
		} finally {
			try {
				if (fop != null) {
					fop.close();
				}
				if(writer != null){
					writer.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static synchronized void read(Context context) {
		boolean flag = true;
		String path = context.getFilesDir().getPath();
		Log.e("ashenhjhj", "path:"+path);
		String paths = path + file_path;
		Log.e("ashenhjhjededede", "path:"+paths);
		//if(flag) return;
		file = new File(paths);
		FileInputStream fin = null;
		try {
			System.out.println("以字节为单位读取文件内容，一次读多个字节：");
			fin = new FileInputStream(file);
			int length = fin.available();
			byte[] tempbytes = new byte[length];
			showAvailableBytes(fin);
			fin.read(tempbytes);
			String s = EncodingUtils.getString(tempbytes, "UTF-8");
			System.out.println(s.length());
			//Log.e("ashenFileaaaaa", "content:"+s.length());
			String a = GzipUtil.gzip(s);
			//Log.e("ashenFileaaaaa", "after Base64:"+a.length());
			//Log.e("ashenFileaaaaa", "base64 decode:"+GzipUtil.gunzip(a));
			HttpUtils utils = new HttpUtils();
			RequestParams params = new RequestParams("UTF-8");
			params.addBodyParameter("data",a);
			//Log.e("ashenFileaaaaa", "post");
			utils.send(HttpMethod.POST, "http://10.58.26.2:8080/log/collection", params,new RequestCallBack<String>() {
				@Override
				public void onFailure(HttpException arg0, String arg1) {
					
				}

				@Override
				public void onSuccess(ResponseInfo<String> arg0) {
					//file.delete();
					Log.e("ashenFileaaasssaa", "base64 decode success:"+arg0.result);
					try {
						JSONObject object = new JSONObject(arg0.result);
						String  status = object.getString("status");
						if(TextUtils.equals("0", status)){
							file.delete();
							Log.e("asasasasasasas", "delete.."+status);
						}
						Log.e("asasasasasasas", "status.."+status);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						Log.e("asasasasasasas", "wrong");
					}
				}
			});
		//	//Log.e("ashenFileaaaaa", "base64 decode:"+gunzip(gzip(s)));
			//Wfile.delete();
		} catch (Exception e1) {
			e1.printStackTrace();
			//Log.e("ashenFileaaaaa", "error");
		} finally {
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e1) {
				}
			}
		}

	}

	/*public static void uploadFile(Context context) {
		String end = "/r/n";
		String Hyphens = "--";
		String boundary = "*****";
		// 要上传的本地文件路径
		String path = context.getFilesDir().getPath();
		Log.e("ashenhjhj", "path:"+path);
		String paths = path + "/newfile.txt";
		File uploadFile = new File(paths);
		// 上传到服务器的指定位置
		String actionUrl = "http://192.168.100.100:8080/upload/upload.jsp";
		try {
			URL url = new URL(actionUrl);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			 允许Input、Output，不使用Cache 
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			 设定传送的method=POST 
			con.setRequestMethod("POST");
			 setRequestProperty 
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			con.setRequestProperty("Accept-Encoding",
                     "gzip,deflate,sdch");//采用gzip的方式进行压缩
			 设定DataOutputStream 
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			 取得文件的FileInputStream 
			FileInputStream fStream = new FileInputStream(uploadFile);
			 设定每次写入1024bytes 
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			 从文件读取数据到缓冲区 
			while ((length = fStream.read(buffer)) != -1) {
				 将数据写入DataOutputStream中 
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(Hyphens + boundary + Hyphens + end);
			fStream.close();
			ds.flush();
			 取得Response内容 
			InputStream is = con.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			System.out.println("上传成功");
			uploadFile.delete();//上传成功后删除
			ds.close();
		} catch (Exception e) {
			System.out.println("上传失败" + e.getMessage());
		}

	}
	*/
	
	public static String BASE64Decode(String s){
		return new String(Base64.decode(s, 0));
	}
	
	/**
	 * base64编码
	 * @param s
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String BASE64(String s) throws UnsupportedEncodingException{
		if(s==null) return null;
				return Base64.encodeToString(s.getBytes("UTF-8"), Base64.DEFAULT);
	}

	
	

}