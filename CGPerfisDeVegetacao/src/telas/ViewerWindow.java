package telas;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import org.apache.batik.swing.JSVGCanvas;
import org.apache.batik.swing.gvt.GVTTreeRendererAdapter;
import org.apache.batik.swing.gvt.GVTTreeRendererEvent;
import org.apache.batik.swing.svg.SVGDocumentLoaderAdapter;
import org.apache.batik.swing.svg.SVGDocumentLoaderEvent;
import org.apache.batik.swing.svg.GVTTreeBuilderAdapter;
import org.apache.batik.swing.svg.GVTTreeBuilderEvent;
import org.w3c.dom.svg.SVGDocument;


public class ViewerWindow {

    public static void main(String[] args,SVGDocument doc) {
        // Create a new JFrame.
        JFrame f = new JFrame("Visualizador SVG");
        ViewerWindow app = new ViewerWindow(f);
        
        if (doc == null)//em caso do arquivo a ser lido nao esta em tempo de execucao
        	svgCanvas.setURI(args[0]);
        else//quando o arquivo a ser exibido vem sofrendo alteraçoes ao longo do tempo de execução
        	svgCanvas.setSVGDocument(doc);
        
        // Add components to the frame.
        f.getContentPane().add(app.createComponents());
        
        //obtendo dimensoes do resultado para plotar na tela
        int  width = Integer.parseInt(doc.getRootElement().getAttribute("width"));
        int height = Integer.parseInt(doc.getRootElement().getAttribute("height"));
        f.setSize(width + widthCorrection,height + heightCorrection);
        f.setVisible(true);
    }
    
    // The frame.
    protected JFrame frame;

    // The "Load" button, which displays up a file chooser upon clicking.
    protected JButton button = new JButton("Carregar SVG");
    
   

    // The status label.
    protected JLabel label = new JLabel();

    // The SVG canvas.
    protected static JSVGCanvas svgCanvas = new JSVGCanvas();

    
    protected static int heightCorrection = 65 ;
    protected static int widthCorrection  = 11;
    public ViewerWindow(JFrame f) {
        frame = f;
    }

    public JComponent createComponents() {
        // Create a panel and add the button, status label and the SVG canvas.
        final JPanel panel = new JPanel(new BorderLayout());

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        p.add(button);
        p.add(label);
        
        
        panel.add("North", p);
        panel.add("Center", svgCanvas);

        // Set the button action.
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JFileChooser fc = new JFileChooser(".");
                int choice = fc.showOpenDialog(panel);
                if (choice == JFileChooser.APPROVE_OPTION) {
                    File f = fc.getSelectedFile();
                    svgCanvas.setURI(f.toURI().toString());
                }
            }
        });

        // Set the JSVGCanvas listeners.
        svgCanvas.addSVGDocumentLoaderListener(new SVGDocumentLoaderAdapter() {
            public void documentLoadingStarted(SVGDocumentLoaderEvent e) {
                label.setText("Document Loading...");
            }
            public void documentLoadingCompleted(SVGDocumentLoaderEvent e) {
                label.setText("Document Loaded.");
            }
        });

        svgCanvas.addGVTTreeBuilderListener(new GVTTreeBuilderAdapter() {
            public void gvtBuildStarted(GVTTreeBuilderEvent e) {
                label.setText("Build Started...");
            }
            public void gvtBuildCompleted(GVTTreeBuilderEvent e) {
                label.setText("Build Done.");
                frame.pack();
            }
        });

        svgCanvas.addGVTTreeRendererListener(new GVTTreeRendererAdapter() {
            public void gvtRenderingPrepare(GVTTreeRendererEvent e) {
                label.setText("Rendering Started...");
            }
            public void gvtRenderingCompleted(GVTTreeRendererEvent e) {
                label.setText("");
            }
        });
        
        

        return panel;
    }
}