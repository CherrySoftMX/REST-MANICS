package com.cherrysoft.manics.service.search.utils;

import org.openimaj.image.ImageUtilities;
import org.openimaj.image.pixel.statistics.HistogramModel;
import org.openimaj.math.statistics.distribution.MultidimensionalHistogram;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class ImageAnalysisUtils {

  public static Double[] convertImgUrlToVector(String imgUrl) throws IOException {
    URL imageUrl = new URL(imgUrl);
    MultidimensionalHistogram histogram;
    HistogramModel model = new HistogramModel(20);
    model.estimateModel(ImageUtilities.readMBF(imageUrl));
    histogram = model.histogram.clone();
    return Arrays.stream(histogram.values).boxed().toArray(Double[]::new);
  }

}
