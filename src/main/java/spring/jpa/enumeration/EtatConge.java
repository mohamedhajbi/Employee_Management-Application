package spring.jpa.enumeration;

public enum EtatConge {
    SOLLICITE,
    VALIDE,
    REFUSE,
    ANNULE,
    EN_COURS,
    ARRETE,
    FINI;

    public String toLowerCase() {
        return this.name().toLowerCase();
    }
}


