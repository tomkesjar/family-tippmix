package kt.tippmix.model;

public enum Nation {
    USA("Egyesült Államok"),
    CANADA("Kanada"),
    MEXICO("Mexikó"),
    JAPAN("Japán"),
    NEW_ZEALAND("Új-Zéland"),
    IRAN("Irán"),
    ARGENTINA("Argentína"),
    UZBEGISTAN("Üzbegisztán"),
    SOUTH_KOREA("Dél-Korea"),
    JORDAN("Jordánia"),
    AUSTRALIA("Ausztrália"),
    BRAZIL("Brazília"),
    ECUADOR("Ecuador"),
    URUGUAY("Uruguay"),
    COLOMBIA("Kolumbia"),
    PARAGUAY("Paraguay"),
    MOROCCO("Marokkó"),
    TUNISIA("Tunézia"),
    EGYPT("Egyiptom"),
    ALGERIA("Algéria"),
    GHANA("Ghána"),
    CAPE_VERDE("Zöld-foki Köztársaság"),
    SOUTH_AFRICA("Dél-afrikai Köztársaság"),
    QUATAR("Katar"),
    ENGLAND("Anglia"),
    SAUDI_ARABIA("Szaúd-Arábia"),
    IVORY_COAST("Elefántcsontpart"),
    SENEGAL("Szenegál"),
    FRANCE("Franciaország"),
    CROATIA("Horvátország"),
    PORTUGAL("Portugália"),
    NORWAY("Norvégia"),
    GERMANY("Németország"),
    NETHERLANDS("Hollandia"),
    BELGIUM("Belgium"),
    AUSTRIA("Ausztria"),
    SWITZERLAND("Svájc"),
    SPAIN("Spanyolország"),
    SCOTLAND("Skócia"),
    PANAMA("Panama"),
    CURACAO("Curacao"),
    HAITI("Haiti");

    private String hungarianName;

    private Nation(String hungarianName) {
        this.hungarianName = hungarianName;
    }

    public String getHungarianName() {
        return hungarianName;
    }
}
