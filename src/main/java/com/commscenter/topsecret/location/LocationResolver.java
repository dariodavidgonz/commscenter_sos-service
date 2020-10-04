package com.commscenter.topsecret.location;

import java.util.List;

public interface LocationResolver {

	public SecretPoint getLocation(List<Double> ratios);

}
