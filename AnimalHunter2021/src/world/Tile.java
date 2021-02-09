package world;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glEnable;

public class Tile {
	public static Tile tiles[] = new Tile[255];
	public static byte number_of_tiles = 0;
	
	public static final Tile test_tile = new Tile("grass_tile");
	public static final Tile test2 = new Tile("sea_tile").setSolid();
	public static final Tile test3 = new Tile("tree").setSolid();
	
	private byte id;
	private boolean solid;
	private String texture;

	public Tile(String texture) {
		this.id = number_of_tiles;
		number_of_tiles++;
		this.texture = texture;
		this.solid = false;

		if (tiles[id] != null) {
			throw new IllegalStateException("Tiles at: [" + id + "] is already being used!");
		}
		tiles[id] = this;
	}
	
	public Tile setSolid() {
		this.solid = true;
		return this;
	}
	
	public boolean isSolid() {
		return solid;
	}

	public byte getId() {
		return id;
	}

	public String getTexture() {
		return texture;
	}
}
