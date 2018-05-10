package com.christian.codingChallenge.Controllers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class mainController {
	@RequestMapping("/")
	private String index() {
		return "index.jsp";
	}

	String fileName;

	@PostMapping("/upload") // //new annotation since 4.3
	public String singleFileUpload(HttpSession session, @RequestParam("file") MultipartFile file,
			RedirectAttributes redirectAttributes, Model model) {
		String hash = "";
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
			return "redirect:uploadStatus";
		}
		File a = new File("");
		String TEST = a.getAbsolutePath().replace("\\", "//");
		fileName = TEST + "//" + file.getOriginalFilename();
		session.setAttribute("fileName", file.getOriginalFilename());
		byte[] bytes;
		try {
			bytes = file.getBytes();
			Path path = Paths.get(fileName);
			Files.write(path, bytes);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println(fileName);
		try {
			System.out.println(MD5(retrieveFromTxt(fileName)));
			// System.out.println(retrieveFromTxt(fileName));
			hash = MD5(retrieveFromTxt(fileName));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// utility = null;

		try {
			URL url = new URL("https://api.metadefender.com/v2/hash/" + hash);
			String API_KEY = "432ca0d425414142391ebcaa92a98f26";
			System.out.println("MAKING CALL: " + hash);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("apikey", API_KEY);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			String hashOutput = "";
			while ((inputLine = in.readLine()) != null) {
				hashOutput += inputLine;
			}
			in.close();
			session.setAttribute("hashOutput", hashOutput);
			System.out.println(con.getResponseCode());
			System.out.println(hashOutput.toString());

			if (hashOutput.contains("Not Found")) {
				url = new URL("https://api.metadefender.com/v2/file");
				con = (HttpURLConnection) url.openConnection();
				con.setRequestMethod("POST");
				con.setRequestProperty("apikey", API_KEY);

				Map<String, String> parameters = new HashMap<>();
				parameters.put("formData", fileName);

				con.setDoOutput(true);
				DataOutputStream out = new DataOutputStream(con.getOutputStream());
				out.writeBytes(ParameterStringBuilder.getParamsString(parameters));
				out.flush();
				out.close();

				in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				inputLine = "";

				InputStream inputStream = con.getInputStream();
				JSONParser jsonParser = new JSONParser();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(new InputStreamReader(inputStream, "UTF-8"));
				// System.out.println(uploadOutput.toString());
				System.out.println(jsonObject);
				session.setAttribute("uploadOutput", jsonObject);

				// DATA_ID CHECKED BY HASH (1ST CALL)
				boolean repeat = true;
				while (repeat) {
					url = new URL("https://api.metadefender.com/v2/file/" + jsonObject.get("data_id"));
					System.out.println("MAKING CALL: " + jsonObject.get("data_id"));
					con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("GET");
					con.setRequestProperty("apikey", API_KEY);
					in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					inputStream = con.getInputStream();
					JSONObject jsonObject1 = (JSONObject) jsonParser.parse(new InputStreamReader(inputStream, "UTF-8"));
					if (jsonObject1.toString().contains("progress_percentage\":100")) {
						repeat = false;
					}
					JSONObject obj = (JSONObject) jsonParser.parse(jsonObject1.get("scan_results").toString());

					System.out.println("SCAN_DETAILS" + obj.toString());
					session.setAttribute("hashUploadOutput", jsonObject);
					session.setAttribute("test", obj);
					System.out.println(jsonObject1.toString());
					TimeUnit.SECONDS.sleep(5);
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "redirect:/output";
	}

	@RequestMapping("/output")
	private String output() {
		return "output.jsp";
	}

	@RestController
	private class api {
		@RequestMapping("/getJson")
		private Object getJson(HttpSession session) {
			return session.getAttribute("test");
		}
	}

	String outputfilename = "";

	public static String retrieveFromTxt(String path) throws Exception {
		String buffer = "";
		File myFile = new File(path);
		BufferedReader bf = new BufferedReader(new FileReader(myFile));
		String details = bf.readLine();
		boolean initialLoop = false;
		while (details != null) {
			buffer += details;
			if (initialLoop)
				buffer += "\n";
			else
				initialLoop = true;
			details = bf.readLine();
		}

		bf.close();
		return buffer;
	}

	public String MD5(String md5) {
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			byte[] array = md.digest(md5.getBytes());
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}

}
