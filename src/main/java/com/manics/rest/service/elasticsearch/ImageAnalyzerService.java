package com.manics.rest.service.elasticsearch;

import org.openimaj.image.ImageUtilities;
import org.openimaj.image.pixel.statistics.HistogramModel;
import org.openimaj.math.statistics.distribution.MultidimensionalHistogram;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

@Service
public class ImageAnalyzerService {

  public Double[] analyzeImageFormUrl(String urlImage) throws IOException {
    URL imageUrl = new URL(urlImage);
    MultidimensionalHistogram histogram;
    HistogramModel model = new HistogramModel(20);
    model.estimateModel(ImageUtilities.readMBF(imageUrl));
    histogram = model.histogram.clone();
    return Arrays.stream(histogram.values).boxed().toArray(Double[]::new);
  }

}
