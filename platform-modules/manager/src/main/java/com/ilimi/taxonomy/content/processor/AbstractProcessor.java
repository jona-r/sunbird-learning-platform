package com.ilimi.taxonomy.content.processor;

import com.ilimi.taxonomy.content.concrete.processor.BaseConcreteProcessor;
import com.ilimi.taxonomy.content.entity.Plugin;

/**
 * The Class <code>AbstractProcessor</code> is the base of All the Concrete
 * Processor thus helps to play all Concrete Processor in
 * <code>Pipes and Filter</code> Design Pattern. It can be used in two ways
 * <code>Automatic Chain</code> and <code>Explicit Chain</code>. The Automatic
 * Chain can be initialized by setting <code>setNextProcessor</code> field
 * pointing to instance of next processor. For Explicit initialization
 * <code>ContentPipelineProcessor</code> needs to be used and its register
 * method for registering the individual Processor.
 * 
 * <p>
 * All the Concrete Processor should be designed in a way that they can be
 * registered or chained in any sequence i.e. Each Processor should concentrate
 * on its own atomic task, there should be no dependency on any other processor
 * for its operation.
 *
 * 
 * @author Mohammad Azharuddin
 * 
 * @see AssessmentItemCreatorProcessor
 * @see AssetCreatorProcessor
 * @see AssetsValidatorProcessor
 * @see BaseConcreteProcessor
 * @see EmbedControllerProcessor
 * @see GlobalizeAssetProcessor
 * @see LocalizeAssetProcessor
 * @see MissingAssetValidatorProcessor
 * @see MissingControllerValidatorProcessor
 * @see ContentPipelineProcessor
 * 
 */
public abstract class AbstractProcessor extends BaseConcreteProcessor {

	/**
	 * The base path is the location on disk where all the File Handling and
	 * manipulation will take place.
	 */
	protected String basePath;

	/** The content id is the identifier of Content <code>Node</code> */
	protected String contentId;

	/**
	 * The next processor points to the instance of Next Processor which needs
	 * to be executed.
	 */
	protected AbstractProcessor nextProcessor;

	/**
	 * Sets the next processor.
	 *
	 * @param nextProcessor
	 *            the new next processor
	 */
	public void setNextProcessor(AbstractProcessor nextProcessor) {
		this.nextProcessor = nextProcessor;
		this.isAutomaticChainExecutionEnabled = true;
	}

	/** Set it to <code>true</code> for enabling automatic chain execution. */
	protected boolean isAutomaticChainExecutionEnabled = false;

	/**
	 * Sets the flag if automatic chain execution enabled is set.
	 *
	 * @param isAutomaticChainExecutionEnabled
	 *            the new flag to enable automatic chain execution.
	 */
	public void setIsAutomaticChainExecutionEnabled(boolean isAutomaticChainExecutionEnabled) {
		this.isAutomaticChainExecutionEnabled = isAutomaticChainExecutionEnabled;
	}

	/**
	 * Execute.
	 *
	 * @param content
	 *            the content is the ECRF Object.
	 * @return the processed ECRF Object
	 */
	public Plugin execute(Plugin content) {
		content = process(content);
		if (null != nextProcessor && isAutomaticChainExecutionEnabled == true) {
			content = nextProcessor.execute(content);
		}
		return content;
	}

	/**
	 * Process is an Abstract Method for which the Concrete Processor of Content
	 * Work-Flow Pipeline needs to provide implementation. All the Logic of
	 * Concrete Processor should start and finish from at this method.
	 *
	 * @param content
	 *            The ECRF Object to Process.
	 * @return The processed ECRF Object
	 */
	abstract protected Plugin process(Plugin content);
}
