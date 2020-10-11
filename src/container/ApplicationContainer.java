package container;

public interface ApplicationContainer {

	void beanDefinitionReload(String configFileName);

	Object generator(String instanceName);

	Object generator();

	Object getAction(String actionName);

}
