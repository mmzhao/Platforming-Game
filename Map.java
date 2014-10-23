import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Map {
	char[][] map;
	String name;
	String bg1;
	String bg2;
	
	public Map(String f) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(f));
		//file must be in format:
		//Line 1: Map Name
		//Line 2: Background Image1
		//Line 3: Background Image2
		//Line 4: size of Map in form m * n, where 5mx5n is the total map size
		//Line 5: row for column indices
		//Line 6 -- (n / 5 + 5): row number and map 5x5 objects
		//P - platform, B - baddie, M - player
		
		name = br.readLine();
		bg1 = br.readLine();
		bg2 = br.readLine();
		String s = br.readLine();
		String[] dim = s.split(" ");
		map = new char[Integer.parseInt(dim[1])][Integer.parseInt(dim[0])];
		br.readLine();
		for(int i = 0; i < map.length; i++){
			s = br.readLine();
//			System.out.println("s " + s);
			for(int j = 0; j < map[0].length; j++){
				map[i][j] = s.charAt(j + 5);
			}
		}
//		for(int i = 0; i < map.length; i++){
//			for(int j = 0; j < map[0].length; j++){
//				System.out.print(map[i][j] + " ");
//			}
//			System.out.println();
//		}
	}
	
	public void initializeMap(GamePanel g){
		for(int i = 0; i < map.length; i++){
//			for(int j = 0; j < map[0].length; j++){
//				System.out.print(j%10);
//			}System.out.println();
			for(int j = 0; j < map[0].length; j++){
//				System.out.print(map[i][j]);
				if(map[i][j] == '0'){
//					continue;
				}
				else if(map[i][j] == 'M'){
					g.setPlayer(new Player(g.loadImage("Standing.png"), j * 5, i * 5, 20, 20, 100, null));
				}
				else if(map[i][j] == 'B'){
//					GamePanel.getEL().addEntity(new Baddie(null, j * 5, i * 5, 20, 20, true, -3 * Math.pow(-1, (int) (Math.random() * 2)), 0, 1000000));
					GamePanel.getEL().addEntity(new Baddie(null, j * 5, i * 5, 20, 20, true, 0, 0, 1000000));
					}
				else if(map[i][j] == 'P'){
//					GamePanel.getEL().addEntity((new Platform(null, j * 5, i * 5, 5, 5)));
					int length = 1;
					while(j + length < map[0].length){
						if(map[i][j + length] == 'P'){
							length++;
						}
						else{
							break;
						}
					}
					GamePanel.getEL().addEntity(new Platform(null, j * 5, i * 5, length * 5, 5));
					j += (length - 1);
				}
			}
//			System.out.println();
		}
	}
	
	
}
