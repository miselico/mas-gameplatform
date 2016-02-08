package fi.jyu.ties454.assignment3.infra;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import fi.jyu.ties454.assignment3.infra.Device.AvailableDevice;

public class PartsShop {

	private final ImmutableMap<String, Part> parts;

	public PartsShop() {
		this(DefaultDevices.class);
	}

	public PartsShop(Class<?> partsContainer) {
		Builder<String, Part> b = new Builder<>();

		Class<?>[] innerClasses = partsContainer.getDeclaredClasses();
		for (Class<?> class1 : innerClasses) {
			AvailableDevice anot = class1.getAnnotation(Device.AvailableDevice.class);

			if (anot != null) {
				int price = anot.cost();
				if (Device.class.isAssignableFrom(class1)) {
					@SuppressWarnings("unchecked")
					Class<? extends Device> deviceClass = (Class<? extends Device>) class1;
					Constructor<? extends Device> cons;
					try {
						cons = deviceClass.getDeclaredConstructor(Floor.class, AgentState.class);
					} catch (NoSuchMethodException | SecurityException e) {
						throw new Error(e);
					}
					Part part = new Part(cons, price);
					b.put(deviceClass.getName(), part);
				} else {
					throw new Error("AvailableDevice annotation on someting which is not a " +  Device.class.getName());
				}
			}
		}
		this.parts = b.build();
	}

	public boolean partExists(String part) {
		return parts.containsKey(part);
	}

	public int getPrice(String part) {
		Preconditions.checkArgument(partExists(part));
		return parts.get(part).price;
	}

	public void attachPart(String device, Floor map, AgentState state) {
		Part part = parts.get(device);
		try {
			Device d = part.constructor.newInstance(map, state);
			//double dispatch
			d.attach(state.agent);
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new Error(e);
		}
	}

	public static class Part {
		public final Constructor<? extends Device> constructor;
		public final int price;

		public Part(Constructor<? extends Device> cons, int price) {
			super();
			this.constructor = cons;
			this.price = price;
		}
		
		@Override
		public String toString() {
			return MoreObjects.toStringHelper(this).add("class", constructor.getDeclaringClass().getName()).add("price", price).toString();
		}
	}

	@Override
	public String toString() {
		return parts.toString();
	}
	
}
