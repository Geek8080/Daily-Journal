package org.geek8080.journal.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.geek8080.journal.entities.Diary;
import org.geek8080.journal.entities.Page;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PDFReport {

	private static final Logger LOGGER = LogManager.getLogger(PDFReport.class);

	public static void generatePDF(String location, String fileName, Diary diary) throws RuntimeException {
		HashMap<Integer, Page> pages = diary.getPages();

		LOGGER.info("Generating Document[PDF Report].");

		try(PDDocument document = new PDDocument()){
			pages.forEach((pageID, diaryPage) -> {
				String title = diaryPage.getTitle();
				String subTitle = diaryPage.getSubTitle();
				String time = diaryPage.getCreationTimeString();
				String body = diaryPage.getBody().toString();

				PDPage page = new PDPage();
				float hMargin = 20;
				float vMargin = 30;
				float px = hMargin, py = vMargin;
				float leading;

				// Writing Title
				try(PDPageContentStream titleContentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true )) {
					PDFont titleFont = PDType1Font.TIMES_ROMAN;
					float titleFontSize = 24;
					float titleWidth = titleFont.getStringWidth(title) / 1000 * titleFontSize;
					float titleHeight = titleFont.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * titleFontSize;
					titleContentStream.beginText();
					titleContentStream.setFont(titleFont, titleFontSize);
					px = (page.getMediaBox().getWidth() - titleWidth) / 2;
					py = page.getMediaBox().getHeight() - titleHeight - vMargin;
					titleContentStream.newLineAtOffset(px, py);
					titleContentStream.setNonStrokingColor(Color.BLACK);
					titleContentStream.showText(title);
					titleContentStream.endText();
				} catch (IOException ex) {
					LOGGER.error("Encountered an error while writing title to PDF." + title, ex);
					throw new RuntimeException("Encountered an error while writing title to PDF." + title, ex);
				}


				// Writing the subtitle and date
				try(PDPageContentStream subtitleContentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
					PDFont subtitleFont = PDType1Font.TIMES_ITALIC;
					float subtitleFontSize = 16;
					leading = 1.5f * subtitleFontSize;
					List<String> subtitleLines = breakLongString(subTitle, subtitleFontSize, subtitleFont, page.getMediaBox().getWidth() - 2 * hMargin);
					px = hMargin;
					py = py - 60;
					subtitleContentStream.beginText();
					subtitleContentStream.setFont(subtitleFont, subtitleFontSize);
					subtitleContentStream.newLineAtOffset(px, py);
					subtitleContentStream.setNonStrokingColor(Color.GRAY);
					for (String line :
							subtitleLines) {
						subtitleContentStream.showText(line);
						subtitleContentStream.newLineAtOffset(0, -leading);
						py -= leading;
					}
					subtitleContentStream.showText(time);
					subtitleContentStream.endText();
				} catch (IOException ex) {
					LOGGER.error("Encountered an error while writing subtitle/time to PDF." + title, ex);
					throw new RuntimeException("Encountered an error while writing subtitle/time to PDF." + title, ex);
				}

				// Writing the Body
				try(PDPageContentStream bodyContentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
					PDFont bodyFont = PDType1Font.HELVETICA;
					float bodyFontSize = 14;
					leading = 1.5f * bodyFontSize;
					String[] parts = body.split("\n");
					System.out.println(Arrays.toString(parts));
					List<String> bodyParts = new ArrayList<>();
					hMargin += 10;
					for (String str : parts) {
						bodyParts.addAll(breakLongString(str, bodyFontSize, bodyFont, page.getMediaBox().getWidth() - 2 * hMargin));
					}
					px = hMargin;
					py = py - 60;
					bodyContentStream.beginText();
					bodyContentStream.setFont(bodyFont, bodyFontSize);
					bodyContentStream.newLineAtOffset(px, py);
					bodyContentStream.setNonStrokingColor(Color.DARK_GRAY);
					System.out.println(bodyParts.toString());
					for (String line : bodyParts) {
						bodyContentStream.showText(line);
						bodyContentStream.newLineAtOffset(0, -leading);
						py -= leading;
					}
					bodyContentStream.endText();
				} catch (IOException ex) {
					LOGGER.error("Encountered an error while writing body to PDF." + title, ex);
					throw new RuntimeException("Encountered an error while writing body to PDF." + title, ex);
				}


				document.addPage(page);
			});
			document.save(location + fileName + ".pdf");
		} catch (IOException ex) {
			LOGGER.error("Encountered an error while writing to PDF.", ex);
			throw new RuntimeException("Encountered an error while writing to PDF.", ex);
		}
	}

	public static void generatePDF(String location, String fileName, Page diaryPage){
		try(PDDocument document = new PDDocument()){

				String title = diaryPage.getTitle();
				String subTitle = diaryPage.getSubTitle();
				String time = diaryPage.getCreationTimeString();
				String body = diaryPage.getBody().toString();

				PDPage page = new PDPage();
				float hMargin = 20;
				float vMargin = 20;
				float px = hMargin, py = vMargin;
				float leading;

				// Writing Title
				try(PDPageContentStream titleContentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true )) {
					PDFont titleFont = PDType1Font.TIMES_ROMAN;
					float titleFontSize = 24;
					float titleWidth = titleFont.getStringWidth(title) / 1000 * titleFontSize;
					float titleHeight = titleFont.getFontDescriptor().getFontBoundingBox().getHeight() / 1000 * titleFontSize;
					titleContentStream.beginText();
					titleContentStream.setFont(titleFont, titleFontSize);
					px = (page.getMediaBox().getWidth() - titleWidth) / 2;
					py = page.getMediaBox().getHeight() - titleHeight - vMargin;
					titleContentStream.newLineAtOffset(px, py);
					titleContentStream.setNonStrokingColor(Color.BLACK);
					titleContentStream.showText(title);
					titleContentStream.endText();
				} catch (IOException ex) {
					LOGGER.error("Encountered an error while writing title to PDF." + title, ex);
					throw new RuntimeException("Encountered an error while writing title to PDF." + title, ex);
				}


				// Writing the subtitle and date
				try(PDPageContentStream subtitleContentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
					PDFont subtitleFont = PDType1Font.TIMES_ITALIC;
					float subtitleFontSize = 16;
					leading = 1.5f * subtitleFontSize;
					List<String> subtitleLines = breakLongString(subTitle, subtitleFontSize, subtitleFont, page.getMediaBox().getWidth() - 2 * hMargin);
					px = hMargin;
					py = py - 40;
					subtitleContentStream.beginText();
					subtitleContentStream.setFont(subtitleFont, subtitleFontSize);
					subtitleContentStream.newLineAtOffset(px, py);
					subtitleContentStream.setNonStrokingColor(Color.GRAY);
					for (String line :
							subtitleLines) {
						subtitleContentStream.showText(line);
						subtitleContentStream.newLineAtOffset(0, -leading);
						py -= leading;
					}
					subtitleContentStream.showText(time);
					subtitleContentStream.endText();
				} catch (IOException ex) {
					LOGGER.error("Encountered an error while writing subtitle/time to PDF." + title, ex);
					throw new RuntimeException("Encountered an error while writing subtitle/time to PDF." + title, ex);
				}

				// Writing the Body
				try(PDPageContentStream bodyContentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true)) {
					PDFont bodyFont = PDType1Font.HELVETICA;
					float bodyFontSize = 14;
					leading = 1.5f * bodyFontSize;
					String[] parts = body.split("\n");
					System.out.println(Arrays.toString(parts));
					List<String> bodyParts = new ArrayList<>();
					hMargin += 10;
					for (String str : parts) {
						bodyParts.addAll(breakLongString(str, bodyFontSize, bodyFont, page.getMediaBox().getWidth() - 2 * hMargin));
					}
					px = hMargin;
					py = py - 40;
					bodyContentStream.beginText();
					bodyContentStream.setFont(bodyFont, bodyFontSize);
					bodyContentStream.newLineAtOffset(px, py);
					bodyContentStream.setNonStrokingColor(Color.DARK_GRAY);
					System.out.println(bodyParts.toString());
					for (String line : bodyParts) {
						bodyContentStream.showText(line);
						bodyContentStream.newLineAtOffset(0, -leading);
						py -= leading;
					}
					bodyContentStream.endText();
				} catch (IOException ex) {
					LOGGER.error("Encountered an error while writing body to PDF." + title, ex);
					throw new RuntimeException("Encountered an error while writing body to PDF." + title, ex);
				}


				document.addPage(page);
				document.save(location + fileName + ".pdf");
		} catch (IOException ex) {
			LOGGER.error("Encountered an error while writing to PDF.", ex);
			throw new RuntimeException("Encountered an error while writing to PDF.", ex);
		}
	}

	private static List<String> breakLongString(String text, float fontSize, PDFont pdfFont, float width) throws RuntimeException {
		List<String> lines = new ArrayList<>();
		LOGGER.info("Generating list from the String to fit the line.");
		if (text == null || text.equals("")){
			LOGGER.info("Nothing in the text passed... Returning empty list of String.");
			lines.add("");
			return lines;
		}
		int lastSpace = -1;
		while (text.length() > 0)
		{
			int spaceIndex = text.indexOf(' ', lastSpace + 1);
			if (spaceIndex < 0)
				spaceIndex = text.length();
			String subString = text.substring(0, spaceIndex);
			float size = 0;
			try {
				size = fontSize * pdfFont.getStringWidth(subString) / 1000;
			} catch (IOException ex) {
				LOGGER.error("Couldn't get size of the String width.", ex);
				throw new RuntimeException("Couldn't get size of the String width.", ex);
			}
			//System.out.printf("'%s' - %f of %f\n", subString, size, width);
			if (size > width) {
				if (lastSpace < 0)
					lastSpace = spaceIndex;
				subString = text.substring(0, lastSpace);
				lines.add(subString);
				text = text.substring(lastSpace).trim();
				//System.out.printf("'%s' is line\n", subString);
				lastSpace = -1;
			}
			else if (spaceIndex == text.length()) {
				lines.add(text);
				//System.out.printf("'%s' is line\n", text);
				text = "";
			}
			else {
				lastSpace = spaceIndex;
			}
		}

		LOGGER.info("Successfully broken lines into parts.");
		return lines;
	}
}
