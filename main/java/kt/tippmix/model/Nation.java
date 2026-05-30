package kt.tippmix.model;

public enum Nation {
    ALGERIA("Algéria"),
    ARGENTINA("Argentína"),
    AUSTRALIA("Ausztrália"),
    AUSTRIA("Ausztria"),
    BELGIUM("Belgium"),
    BOSNIA_HERZEGOVINA("Bosznia-Hercegovina"),
    BRAZIL("Brazília"),
    CANADA("Kanada"),
    CAPE_VERDE("Zöld-foki Köztársaság"),
    COLOMBIA("Kolumbia"),
    CONGO("Kongói DK"),
    CROATIA("Horvátország"),
    CURACAO("Curacao"),
    CZECH_REPUBLIC("Csehország"),
    ECUADOR("Ecuador"),
    EGYPT("Egyiptom"),
    ENGLAND("Anglia"),
    FRANCE("Franciaország"),
    GERMANY("Németország"),
    GHANA("Ghána"),
    HAITI("Haiti"),
    IRAK("Irak"),
    IRAN("Irán"),
    IVORY_COAST("Elefántcsontpart"),
    JAPAN("Japán"),
    JORDAN("Jordánia"),
    MEXICO("Mexikó"),
    MOROCCO("Marokkó"),
    NETHERLANDS("Hollandia"),
    NEW_ZEALAND("Új-Zéland"),
    NORWAY("Norvégia"),
    PANAMA("Panama"),
    PARAGUAY("Paraguay"),
    PORTUGAL("Portugália"),
    QUATAR("Katar"),
    SAUDI_ARABIA("Szaúd-Arábia"),
    SCOTLAND("Skócia"),
    SENEGAL("Szenegál"),
    SOUTH_AFRICA("Dél-Afrika"),
    SOUTH_KOREA("Dél-Korea"),
    SPAIN("Spanyolország"),
    SWEDEN("Svédország"),
    SWITZERLAND("Svájc"),
    TUNISIA("Tunézia"),
    TURKEY("Törökország"),
    URUGUAY("Uruguay"),
    USA("Egyesült Államok"),
    UZBEGISTAN("Üzbegisztán");



    private String hungarianName;

    private Nation(String hungarianName) {
        this.hungarianName = hungarianName;
    }

    public String getHungarianName() {
        return hungarianName;
    }

    public static Nation fromHungarianName(String hungarianName) {
        for (Nation nation : Nation.values()) {
            if (nation.hungarianName.equals(hungarianName)) {
                return nation;
            }
        }
        throw new IllegalArgumentException(
                "Unknown hungarian name: " + hungarianName
        );
    }
}
