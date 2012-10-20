package controle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.JPEGTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.fop.svg.PDFTranscoder;
import org.w3c.dom.svg.SVGDocument;

public class RasterSVG {

	static public void rasterizing(SVGDocument resp, File output) {
	
		String ext = output.getName().substring(
				output.getName().lastIndexOf(".") + 1,
				output.getName().length());

		ext = ext.toLowerCase();
		/*
		if (ext.equals("jpeg"))
			SaveAsJPEG(resp, output);
		else if (ext.equals("pdf"))
			SaveAsPDF(resp, output);
		else if (ext.equals("png"))
			SaveAsPNG(resp, output);
		else if(ext.equals("svg"))
			SaveAsSVG(resp,output);
		
		// se ext for jpeg chama a funcao apropriada
		*/String copyFileName = output.getAbsolutePath().substring(0,output.getAbsolutePath().lastIndexOf(".") ) + ".svg";
		File copyOutuput = new File(copyFileName);
		SaveAsSVG(resp, copyOutuput);
		
		String parser = XMLResourceDescriptor.getXMLParserClassName();
		SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(parser);
		try {
			SVGDocument respPDF = f.createSVGDocument(copyOutuput.toURI()
					.toString());
			if (ext.equals("jpeg"))
				SaveAsJPEG(resp, output);
			else if (ext.equals("pdf"))
				SaveAsPDF(resp, output);
			else if (ext.equals("png"))
				SaveAsPNG(resp, output);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void SaveAsJPEG(SVGDocument resp, File outputFile) {

		// Create a JPEG transcoder
		JPEGTranscoder t = new JPEGTranscoder();

		// Set the transcoding hints.
		t.addTranscodingHint(JPEGTranscoder.KEY_QUALITY, new Float(.05));
		
		// Create the transcoder input.
		TranscoderInput input = new TranscoderInput(resp.getDocumentURI());

		// Create the transcoder output.
		OutputStream ostream;
		try {
			ostream = new FileOutputStream(outputFile);
			TranscoderOutput output = new TranscoderOutput(ostream);

			// Save the image.
			t.transcode(input, output);

			// Flush and close the stream.
			ostream.flush();
			ostream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TranscoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	private static void SaveAsPDF(SVGDocument resp, File outputFile) {

		// Create a JPEG transcoder
		PDFTranscoder t = new PDFTranscoder();

		// Set the transcoding hints.
		t.addTranscodingHint(PDFTranscoder.KEY_PIXEL_TO_MM, new Float(.1));

		// Create the transcoder input.
		TranscoderInput input = new TranscoderInput(resp.getDocumentURI());

		// Create the transcoder output.
		OutputStream ostream;
		try {
			ostream = new FileOutputStream(outputFile);
			TranscoderOutput output = new TranscoderOutput(ostream);

			// Save the image.
			t.transcode(input, output);

			// Flush and close the stream.
			ostream.flush();
			ostream.close();
		} catch (FileNotFoundException e) {
			System.out.println("Erro de arquivo");
			e.printStackTrace();
		} catch (TranscoderException e) {
			System.out.println("Erro trasncoder");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Erro de io");
			e.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	private static void SaveAsPNG(SVGDocument resp, File outputFile) {

		// Create a JPEG transcoder
		PNGTranscoder t = new PNGTranscoder();

		// Set the transcoding hints.
		t.addTranscodingHint(PNGTranscoder.KEY_PIXEL_TO_MM, new Float(.1));

		// Create the transcoder input.
		TranscoderInput input = new TranscoderInput(resp.getDocumentURI());

		// Create the transcoder output.
		OutputStream ostream;
		try {
			ostream = new FileOutputStream(outputFile);
			TranscoderOutput output = new TranscoderOutput(ostream);
			System.out.println();
			// Save the image.
			t.transcode(input, output);

			// Flush and close the stream.
			ostream.flush();
			ostream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TranscoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void SaveAsSVG(SVGDocument resp, File outputFile) {
		// TODO esta dando erro esta implementacao o importante a observar que
		// existe algo similar nos exemplos

		SVGGraphics2D svgGenerator = new SVGGraphics2D(resp);
		
		// Finally, stream out SVG to the standard output using // UTF-8
		// encoding.
		boolean useCSS = true; // we want to use CSS style attributes

		OutputStream ostream;
		try {
			ostream = new FileOutputStream(outputFile);
			Writer out = new OutputStreamWriter(ostream, "UTF-8");
			System.out.println("Teste de raster para svg");
			svgGenerator.stream(resp.getRootElement(),out, useCSS,true);
			
			ostream.flush();
			ostream.close();
			

		} catch (FileNotFoundException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SVGGraphics2DIOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
