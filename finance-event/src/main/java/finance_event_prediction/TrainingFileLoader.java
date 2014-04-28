package finance_event_prediction;

import java.io.File;

public class TrainingFileLoader {

	public void trainByTestNews(String path){
		File file = new File(path);
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files.length == 0) {
				System.out.println("文件夹是空的!");
				return;
			} else {
				for (File file2 : files) {
					if (file2.isDirectory()) {
						 System.out.println("文件夹:" + file2.getAbsolutePath());
						trainByTestNews(file2.getAbsolutePath());
					} else {
						 System.out.println("文件:" + file2.getAbsolutePath());
						 processFile(file2);
					}
				}
			}
		} else {
			System.out.println("文件不存在!");
		}
	}
	
	protected void processFile(File file){
		
	}
}
