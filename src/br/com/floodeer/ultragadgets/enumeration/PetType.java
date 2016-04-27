package br.com.floodeer.ultragadgets.enumeration;

public enum PetType {
	
	SHEEP("SHEEP"),
	WOLF("WOLF"),
	CAT("CAT"),
	ENDERMAN("ENDERMAN"),
	SLIME("SLIME"),
	ZOMBIE("ZOMBIE"),
	VILLAGER("VILLAGER"),
	SKELETON("SKELETON"),
	CUSTOM_SWAG("SWAG"),
	ENDERMITE("ENDERMITE");
	
	private String pettype;
	
	PetType(String pet) {
		this.pettype = pet;
	}
	
	@Override
	public String toString() {
		return pettype;
	}
	
	public PetType get(String from) {
		for(PetType pets : PetType.values()) {
			if(pets.toString().equalsIgnoreCase(from)) {
				return pets;
			}
		}
		return null;
	}
}
