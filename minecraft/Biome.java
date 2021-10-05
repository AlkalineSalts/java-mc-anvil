package minecraft;


public enum Biome {
    Ocean ((byte)0),
    Plains ((byte)1),
    Desert ((byte)2),
    Extreme_Hills ((byte)3),
    Forest ((byte)4),
    Taiga ((byte)5),
    Swampland ((byte)6),
    River ((byte)7),
    Hell ((byte)8),
    Sky ((byte)9),
    FrozenOcean ((byte)10),
    FrozenRiver ((byte)11),
    IceFlats ((byte)12),
    IceMountains ((byte)13),
    Mushroom_Island ((byte)14),
    Mushroom_Island_Shore ((byte)15),
    Beach ((byte)16),
    DesertHills ((byte)17),
    ForestHills ((byte)18),
    TaigaHills ((byte)19),
    ExtremeHillsEdge ((byte)20),
    Jungle ((byte)21),
    JungleHills ((byte)22),
    JungleEdge ((byte)23),
    DeepOcean ((byte)24),
    StoneBeach ((byte)25),
    ColdBeach ((byte)26),
    BirchForest ((byte)27),
    BirchForestHills ((byte)28),
    RoofedForest ((byte)29),
    ColdTaiga ((byte)30),
    ColdTaigaHills ((byte)31),
    RedwoodTaiga ((byte)32),
    RedwoodTaigaHills ((byte)33),
    ExtremeHillPlus ((byte)34),
    Savanna ((byte)35),
    SavannaPlateau ((byte)36),
    Mesa ((byte)37),
    MesaPlateauF ((byte)38),
    MesaPlateau ((byte)39),
    TheVoid ((byte)127),
    SunflowerPlains ((byte)129),
    DesertM ((byte)130),
    ExtremeHillsM ((byte)131),
    FlowerForest ((byte)132),
    TaigaM ((byte)133),
    SwamplandM ((byte)134),
    IcePlainsSpikes ((byte)140),
    JungleM ((byte)149),
    JungleEdgeM ((byte)151),
    BirchForestM ((byte)155),
    BirchForestHillsM ((byte)156),
    RoofedForestM ((byte)157),
    ColdTaigaM ((byte)158),
    MegaSpruceTaiga ((byte)160),
    RedwoodTaigaHillsM ((byte)161),
    ExtremeHillsPlusM ((byte)162),
    SavannaM ((byte)163),
    SavannaPlateauM ((byte)164),
    MesaM ((byte)165),
    MesaPlateauFM ((byte)166),
    MesaPlateauM ((byte)166);
    
    private final byte biomeId;   // in kilograms
    Biome(byte id) {
        biomeId = id;
    }
    public byte getId() {return biomeId;}
}