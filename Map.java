import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

//initializes each map based on pre-created text file
public class Map {
//file must be in format:
	//Line 1: Map Name
	//Line 2: Background Image1
	//Line 3: Background Image2
	//Line 4: size of Map in form m * n, where 5mx5n is the total map size
	//Line 5: row for column indices
	//Line 6 -- (n / 5 + 5): row number and map 5x5 objects
	
//	map: character array representation of the text file map block
//		'M' - player
//		'B' - baddie
//		'P' - platform
//		'.' or '-' - nothing
//	name: name of the map
//	bg1: path to background1
//	bg2: path to background2
	char[][] map;
	String name;
	String bg1;
	String bg2;
	
	static public HashMap<String, BufferedImage> dict;
	
	public static final int UNIT = 5;
	
// --------------------------------CONSTRUCTOR-------------------------------- //
	
	public Map(String f) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(f));
		name = br.readLine();
		bg1 = br.readLine();
		bg2 = br.readLine();
		String s = br.readLine();
		String[] dim = s.split(" ");
		map = new char[Integer.parseInt(dim[1])][Integer.parseInt(dim[0])];
		br.readLine();
		for(int i = 0; i < map.length; i++){
			s = br.readLine();
			for(int j = 0; j < map[0].length; j++){
				map[i][j] = s.charAt(j + 5);
			}
		}
		dict = new HashMap<String, BufferedImage>();
//		System.out.println(dict.keySet().size());
		br.readLine();
		int numSVGs = Integer.parseInt(br.readLine());
		for(int i = 0; i < numSVGs; i++){
			s = br.readLine();
			String[] a = s.split(" ");
			dict.put(a[0].substring(0, a[0].length() - 4), ImageGetter.getSVG(a[0], Integer.parseInt(a[1]), Integer.parseInt(a[2])));
		}
//		System.out.println(dict.size());
//		for(String key: dict.keySet()){
//			System.out.println("e");
//			System.out.println(key);
//		}
	}
	
// ------------------------------MAKES REAL MAP------------------------------ //
	
	public void initializeMap(GamePanel g){
		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[0].length; j++){
				if(map[i][j] == '.' || map[i][j] == '-'){
					continue;
				}
				else if(map[i][j] == 'M'){
					GamePanel.setPlayer(new Player(this.getDict("Standing2"), j * 5, i * 5, 31, 40, 100, null));
//					GamePanel.setPlayer(new Player(ImageGetter.getSVG("Standing2.svg", 832, 1080), j * 5, i * 5, 31, 40, 100, null));
//					GamePanel.setPlayer(new Player(g.loadImage("Standing.png"), j * 5, i * 5, 20, 20, 100, null));
				}
				else if(map[i][j] == 'B'){
//					GamePanel.getEL().addEntity(new Baddie(null, j * 5, i * 5, 20, 20, true, -3 * Math.pow(-1, (int) (Math.random() * 2)), 0, 1000000));
					GamePanel.getEL().addEntity(new Baddie(null, j * 5, i * 5, 50, 50, true, 0, 0, 1000000));
					}
				else if(map[i][j] == 'P'){
//					System.out.println(i + " " + j);
					int lengthX = 1;
					int lengthY = 1;
					while(j + lengthX < map[0].length){
						if(map[i][j + lengthX] == 'P'){
							lengthX++;
						}
						else{
							break;
						}
					}
//					System.out.println(lengthX);
					while(i + lengthY < map.length){
						boolean addLine = true;
						for(int k = j; k < j + lengthX; k++){
							if(map[i + lengthY][k] != 'P'){
								addLine = false;
								break;
							}
						}
						if(!addLine){
							break;
						}
						lengthY++;
					}
					GamePanel.getEL().addEntity(new Platform(null, j * 5, i * 5, lengthX * 5, lengthY * 5));
//					System.out.println(j * 5 + " " + i * 5 + " " + lengthX * 5 + " " + lengthY * 5);
					for(int k = i; k < i + lengthY; k++){
						for(int l = j; l < j + lengthX; l++){
							map[k][l] = '0';
						}
					}
					map[i][j] = 'P';
				}
			}
		}
		
//		for(int i = 0; i < map.length; i++){
//			for(int j = 0; j < map[0].length; j++){
//				System.out.print(map[i][j]);
//			}System.out.println();
//		}
		
	}
	
	
	public BufferedImage getDict(String key){
		return dict.get(key);
	}
	
	
}
