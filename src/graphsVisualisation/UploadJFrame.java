package graphsVisualisation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * An interface to help the user choosing the documents that he has to upload
 */
public class UploadJFrame extends JFrame {
	private static final long serialVersionUID = -7498139402373436629L;
	
	//Constants for the jframe
	private final String FRAME_NAME = "Upload de documents";
	private final boolean IS_VISIBLE = true;
	private final boolean IS_RESIZABLE = false;
	private final short WIDTH = 900;
	private final short HEIGHT = 650;	
	
	//Constants for title panel
	private final String TITLE_LABEL_NAME = "Upload de documents";
    private final byte TITLE_SIZE = 25;
	
    //Constants for the labels
    private final String FILE_CHOSEN_PREFIX = "Fichier choisi : ";
    private final String NO_FILE_CHOSEN = "Aucun fichier choisi !";
    private final byte FILE_CHOSEN_MAX_LENGTH = 50;
    
    //Constants for the buttons
    private final String DOWNLOAD_BUTTON_TEXT = "Download !";
    private final String UPLOAD_BUTTON_TEXT = "Upload !";
    private final String CHOOSE_BUTTON_TEXT = "Choisir fichier !";
    
    //Constants for the colors
    private final Color DOCUMENTS_PANEL_BORDER_COLOR = Color.GRAY;
    
    //Constants for the paths
    private final String MAIN_ICON_PATH = "ressources/logo.png";
    private final String DOWNLOADED_DOCUMENTS_PATH = "downloaded docs";
    @SuppressWarnings("unused")
	private final String UPLOADED_DOCUMENTS_PATH = "uploaded docs";
    
    //Constants for the JTree
    private final String ROOT_NODE_NAME = "Documents";
    
	//Constants for the font
    private final String TITLE_FONT = "Georgia";
    
    //Constants for the dimension
    private final short TITLE_PANEL_X = WIDTH-2;
    private final short TITLE_PANEL_Y = HEIGHT/10;
    
    private final short DOCUMENTS_PANEL_X = TITLE_PANEL_X-15;
    private final short DOCUMENTS_PANEL_Y = 7*HEIGHT/10;
    
    private final short OPTIONS_PANEL_X = DOCUMENTS_PANEL_X;
    private final short OPTIONS_PANEL_Y = HEIGHT/20;
    
    private final short FILE_CHOSEN_PANEL_X = DOCUMENTS_PANEL_Y;
    private final short FILE_CHOSEN_PANEL_Y = HEIGHT/20;
    
    private final short FILE_CHOSEN_X = OPTIONS_PANEL_X-100;
    private final short FILE_CHOSEN_Y = (OPTIONS_PANEL_Y-10)/2;
    
    private final short TITLE_LABEL_X = TITLE_PANEL_X*3/4;
    private final short TITLE_LABEL_Y = TITLE_PANEL_Y-2;
    
    private final short DOCUMENTS_VIEW_X = DOCUMENTS_PANEL_X;
    private final short DOCUMENTS_VIEW_Y = DOCUMENTS_PANEL_Y-11;
    
    //Constants for the errors for the FileErrorDialog
    private final String NO_FILE_SELECTED = "Aucun fichier n'a été choisi !";
    @SuppressWarnings("unused")
	private final String FILE_EXISTS_IN_DOWNLOAD_DIRECTORY = "Le fichier a déjà été téléchargé !";
    
    //Constants for the file chooser
    private final boolean CAN_SELECT_MULTIPLE_DOCUMENTS = false;
    
    //Main interface
    @SuppressWarnings("unused")
	private VisualisationJFrame main_frame;
    
    //Upload interface
    private JFrame upload_frame;
    
    //JButtons
    private JButton choose_button;
    private JButton download_button;
    private JButton upload_button;
    
	//JPanels
	private JPanel title_panel;
	private JPanel documents_panel;
	private JPanel options_panel;
	private JPanel file_chosen_panel;
	
	//JScrollPanes
	private JScrollPane documents_view;
	
	//DefaultListModel
	private DefaultMutableTreeNode documents_view_node;
	
	//JTree
	private JTree documents_list;
	
	//JLabels
	private JLabel title_label;
	private JLabel file_chosen_label;
	
	//Fonts
	private Font title_font;
	
	//Borders
	private Border documents_panel_border;
	
	//Dimension
	private Dimension title_label_dimension;
	private Dimension documents_view_dimension;
	private Dimension title_panel_dimension;
	private Dimension documents_panel_dimension;
	private Dimension options_panel_dimension;
	private Dimension file_chosen_panel_dimension;
	private Dimension file_chosen_label_dimension;
	
	//Icons
	private ImageIcon main_icon;
	
	//Layouts
	private FlowLayout main_layout;
	
	//Files
	private File chosen_file;
	
	/**
	 * Main constructor
	 *@param main_frame: The main interface
	 */
	public UploadJFrame(VisualisationJFrame main_frame) {
		//Initialization
		this.main_frame = main_frame;
		
		this.upload_frame = this;
		
		this.title_panel = new JPanel();
		this.documents_panel = new JPanel();
		this.file_chosen_panel = new JPanel();
		this.options_panel = new JPanel();
		
		this.documents_view_node = new DefaultMutableTreeNode(ROOT_NODE_NAME);
		this.addDocuments();
		this.documents_list = new JTree(documents_view_node);
		this.documents_view = new JScrollPane(documents_list);
		
		this.choose_button = new JButton(CHOOSE_BUTTON_TEXT);
		this.download_button = new JButton(DOWNLOAD_BUTTON_TEXT);
		this.upload_button = new JButton(UPLOAD_BUTTON_TEXT);
		
		this.title_label = new JLabel(TITLE_LABEL_NAME);
		this.file_chosen_label = new JLabel(FILE_CHOSEN_PREFIX + NO_FILE_CHOSEN);
		
		this.chosen_file = new File("");
		
		this.title_font = new Font(TITLE_FONT, Font.PLAIN, TITLE_SIZE);
		
		this.title_label_dimension = new Dimension(TITLE_LABEL_X, TITLE_LABEL_Y);
		this.title_panel_dimension = new Dimension(TITLE_PANEL_X, TITLE_PANEL_Y);
		this.documents_panel_dimension = new Dimension(DOCUMENTS_PANEL_X, DOCUMENTS_PANEL_Y);
		this.options_panel_dimension = new Dimension(OPTIONS_PANEL_X, OPTIONS_PANEL_Y);
		this.documents_view_dimension = new Dimension(DOCUMENTS_VIEW_X, DOCUMENTS_VIEW_Y);
		this.file_chosen_panel_dimension = new Dimension(FILE_CHOSEN_PANEL_X, FILE_CHOSEN_PANEL_Y);
		this.file_chosen_label_dimension = new Dimension(FILE_CHOSEN_X, FILE_CHOSEN_Y);
		
		this.main_icon = new ImageIcon(MAIN_ICON_PATH);
		
		this.main_layout = new FlowLayout();
		
		//Initialize borders
		this.documents_panel_border = BorderFactory.createLineBorder(DOCUMENTS_PANEL_BORDER_COLOR);
		
		//Settings of the title label
		title_label.setFont(title_font);
		title_label.setHorizontalAlignment(SwingConstants.CENTER);
		title_label.setPreferredSize(title_label_dimension);
		
		//Settings of the documents scrollpane
		documents_view.setPreferredSize(documents_view_dimension);
		
		//Settings of the title panel
		title_panel.setPreferredSize(title_panel_dimension);
		
		//Settings of the documents panel
		documents_panel.setPreferredSize(documents_panel_dimension);
		documents_panel.setBorder(documents_panel_border);
		
		//Settings of the file chosen panel
		file_chosen_panel.setPreferredSize(file_chosen_panel_dimension);
		file_chosen_label.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Settings of the options panel
		options_panel.setPreferredSize(options_panel_dimension);
				
		file_chosen_label.setPreferredSize(file_chosen_label_dimension);
		
		//Adding elements to the title panel
		title_panel.add(title_label);
		
		//Adding elements to the documents panel
		documents_panel.add(documents_view);
		
		//Adding element to the file_chosen_panel
		file_chosen_panel.add(file_chosen_label);
		
		//Adding elements to the options panel
		options_panel.add(choose_button);
		options_panel.add(download_button);
		options_panel.add(upload_button);
		
		//Adding elements to the interface
		this.add(title_panel);
		this.add(documents_panel);
		this.add(file_chosen_panel);
		this.add(options_panel);
		
		//Settings of the interface
		this.setLayout(main_layout);
		this.addListeners();
		this.setResizable(IS_RESIZABLE);
		this.setSize(WIDTH, HEIGHT);
		this.setIconImage(main_icon.getImage());
		this.setTitle(FRAME_NAME);
		this.setVisible(IS_VISIBLE);
	}
	
	/**
	 * To add documents to the list that the user has chosen previously
	 */
	private void addDocuments() {
		for(byte i=1; i<=30; i++) {
			documents_view_node.add(new DefaultMutableTreeNode("- Test"+Integer.toString(i)));
		}
	}
	
	/**
     * To add all the listeners for the interface
     */
	private void addListeners() {
		//Adding a listener for the choose button
		choose_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Choosing file ...");
				
				//Selection of the file to upload
				JFileChooser fc = new JFileChooser();
				fc.setMultiSelectionEnabled(CAN_SELECT_MULTIPLE_DOCUMENTS);
				fc.showOpenDialog(choose_button);
				
				//Processing on the selected file
				chosen_file = fc.getSelectedFile();
				if(chosen_file != null && chosen_file.exists()) {
					String selected_file_path = chosen_file.getAbsolutePath();
					String printable_path = selected_file_path;
					
					//Get a shorter version of the path if it's too long !
					if(selected_file_path.length() > FILE_CHOSEN_MAX_LENGTH) {
						printable_path = selected_file_path.substring(selected_file_path.length()-FILE_CHOSEN_MAX_LENGTH, selected_file_path.length());
					}
					file_chosen_label.setText(FILE_CHOSEN_PREFIX + "(...) " + printable_path);
				}else {
					chosen_file = null;
				}
			}			
		});
		
		//Adding a listener for the download button
		download_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				File download_directory = new File(DOWNLOADED_DOCUMENTS_PATH);
				
				//Creates the repository if it doesn't exist
				if(!download_directory.exists()) {
					download_directory.mkdir();
				}
				
				//Test the validity of the file chosen
				if(chosen_file == null) {
					new FileErrorDialog(upload_frame, NO_FILE_SELECTED);
				}else {
					try {
						Path dest = Paths.get(DOWNLOADED_DOCUMENTS_PATH + File.separator + chosen_file.getName());
						Files.copy(chosen_file.toPath(), dest);
					} catch (Exception e1) {}
				}
			}
		});
		
		//Adding a listener for the upload button
		upload_button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO A changer (bouton upload)
				System.out.println("Uploading document...");
			}			
		});
		
		//Adding a listener for the last selectionned element of the list
		documents_list.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				System.out.println("Document clicked !");				
			}
		});
	}
}
