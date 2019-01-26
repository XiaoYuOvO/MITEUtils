class Main {
   static PosGetter posGetterInstance = new PosGetter();
   static QuickChineseInput quickChineseInputInstance = new QuickChineseInput();
    static void main() {
        posGetterInstance.start();
        quickChineseInputInstance.start();
        new InfoGetThread().start();
    }
}
