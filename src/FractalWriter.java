

import javax.swing.*;

public class FractalWriter {

	public FractalWriter() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		FractalModel model = new FractalModel();
		FractalView view = new FractalView(model);	 
		FractalController controller = new FractalController(model, view);

		view.setVisible(true);
	}

}
