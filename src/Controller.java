mport java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Timer;

/**
 * Do not modify this file without permission from your TA.
  **/
  public class Controller{

  	private Model model;
		private View view;
			private Action drawAction;
				
					Boolean isStop = false;
						int i = 2;
							
								public Controller(){
										view = new View();
												view.getButton().setFocusable(false);
														view.getButton().setMnemonic(KeyEvent.VK_B);
																view.getFrame().addKeyListener(new KeyListener() {
																			@Override
																						public void keyPressed(KeyEvent ke) {
																										switch(ke.getKeyCode()) {
																															case KeyEvent.VK_UP:
																																					model.setDirect(Direction.NORTH);
																																											i = 1;
																																																	break;
																																																						case KeyEvent.VK_DOWN:
																																																												model.setDirect(Direction.SOUTH);
																																																																		i = 2;
																																																																								break;
																																																																													case KeyEvent.VK_LEFT:
																																																																																			model.setDirect(Direction.WEST);
																																																																																									i = 3;
																																																																																															break;
																																																																																																				case KeyEvent.VK_RIGHT:
																																																																																																										model.setDirect(Direction.EAST);
																																																																																																																i = 4;
																																																																																																																						break;
																																																																																																																											case KeyEvent.VK_SPACE:
																																																																																																																																	model.setState(OrcState.JUMP);
																																																																																																																																							break;
																																																																																																																																											}
																																																																																																																																														}
																																																																																																																																																	@Override
																																																																																																																																																				public void keyReleased(KeyEvent ke) {

																																																																																																																																																							}
																																																																																																																																																										@Override
																																																																																																																																																													public void keyTyped(KeyEvent ke) {

																																																																																																																																																																}});
																																																																																																																																																																		
																																																																																																																																																																				view.getButton().addActionListener(new ActionListener() {
																																																																																																																																																																							@Override
																																																																																																																																																																										public void actionPerformed(ActionEvent e) {	
																																																																																																																																																																														isStop = !isStop;
																																																																																																																																																																																		if (isStop == true) {
																																																																																																																																																																																							model.setDirect(Direction.HALT);
																																																																																																																																																																																											}
																																																																																																																																																																																															else if(isStop == false){
																																																																																																																																																																																																				switch(i) {
																																																																																																																																																																																																									case 1:
																																																																																																																																																																																																															model.setDirect(Direction.NORTH);
																																																																																																																																																																																																																					break;
																																																																																																																																																																																																																										case 2:
																																																																																																																																																																																																																																model.setDirect(Direction.SOUTH);
																																																																																																																																																																																																																																						break;
																																																																																																																																																																																																																																											case 3:
																																																																																																																																																																																																																																																	model.setDirect(Direction.WEST);
																																																																																																																																																																																																																																																							break;
																																																																																																																																																																																																																																																												case 4:
																																																																																																																																																																																																																																																																		model.setDirect(Direction.EAST);
																																																																																																																																																																																																																																																																								break;
																																																																																																																																																																																																																																																																													}
																																																																																																																																																																																																																																																																																	}
																																																																																																																																																																																																																																																																																					//t.stop();
																																																																																																																																																																																																																																																																																								}	
																																																																																																																																																																																																																																																																																										});
																																																																																																																																																																																																																																																																																												model = new Model(view.getWidth(), view.getHeight(), view.getImageWidth(), view.getImageHeight());
																																																																																																																																																																																																																																																																																														Controller c = this;
																																																																																																																																																																																																																																																																																																drawAction = new AbstractAction(){
																																																																																																																																																																																																																																																																																																    		public void actionPerformed(ActionEvent e){
																																																																																																																																																																																																																																																																																																		    			c.redraw();
																																																																																																																																																																																																																																																																																																					    		}
																																																																																																																																																																																																																																																																																																							    	};
																																																																																																																																																																																																																																																																																																									}
																																																																																																																																																																																																																																																																																																										
																																																																																																																																																																																																																																																																																																										        //run the simulation
																																																																																																																																																																																																																																																																																																												public void start(){
																																																																																																																																																																																																																																																																																																														EventQueue.invokeLater(new Runnable(){
																																																																																																																																																																																																																																																																																																																	public void run(){
																																																																																																																																																																																																																																																																																																																					Timer t = new Timer(30, drawAction);
																																																																																																																																																																																																																																																																																																																									t.start();
																																																																																																																																																																																																																																																																																																																												}
																																																																																																																																																																																																																																																																																																																														});
																																																																																																																																																																																																																																																																																																																																	//increment the x and y coordinates, alter direction if necessary
																																																																																																																																																																																																																																																																																																																																				//update the view
																																																																																																																																																																																																																																																																																																																																					}
																																																																																																																																																																																																																																																																																																																																						public void redraw(){
																																																																																																																																																																																																																																																																																																																																								model.updateLocationAndDirection();
																																																																																																																																																																																																																																																																																																																																										view.update(model.getX(), model.getY(), model.getDirect());
																																																																																																																																																																																																																																																																																																																																											}

																																																																																																																																																																																																																																																																																																																																											}
