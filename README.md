## Daily-Journal

Designed a simple Application to store diary entries in encrypted form in the H2 database. The application has following features:

- 2147483648 characters in body
- All entries automatically tagged with a time stamp
- Don't like a page?... Just Delete it
- Password protected
- Requires OTP authentication to generate User
- Print the Diary or a Page as PDF
- Get your Diary Report into excel files

The app uses Java 11 with openJFX 14 for material design. The app follows the JPMS conventions and rules for encapsulating. Apart from these the project is maven based and uses these tools:

1. Apache Log4J
2. Apache PDFBox
3. Apache POI
4. Java Mail API
5. Jasypt
6. JFoenix
