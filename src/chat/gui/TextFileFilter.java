package chat.gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import chat.controller.FileUtils;

public class TextFileFilter extends FileFilter {
	@Override
	public boolean accept(File f) {
		boolean is_accept = false;
		if (f.isDirectory()) {
			is_accept = true;
		}else {
			String extension = FileUtils.getExtension(f);
			if (extension != null) {
				if (extension.equals(FileUtils.TXT)) {
					is_accept = true;
				}
			}
		}
		return is_accept;
	}
	@Override
	public String getDescription() {
		return "Just Text File";
	}
}
