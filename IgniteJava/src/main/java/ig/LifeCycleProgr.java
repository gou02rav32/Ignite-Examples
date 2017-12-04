package ig;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.lifecycle.LifecycleBean;
import org.apache.ignite.lifecycle.LifecycleEventType;
import org.apache.ignite.resources.IgniteInstanceResource;

public class LifeCycleProgr {
	public static void main(String[] args) {
        System.out.println();
        System.out.println(">>> Lifecycle example started.");

        // Create new configuration.
        IgniteConfiguration cfg = new IgniteConfiguration();

        LifecycleExampleBean bean = new LifecycleExampleBean();

        // Provide lifecycle bean to configuration.
        cfg.setLifecycleBeans(bean);

        try (Ignite ignite = Ignition.start(cfg)) {
            // Make sure that lifecycle bean was notified about grid startup.
            assert bean.isStarted();
        }

        // Make sure that lifecycle bean was notified about grid stop.
        assert !bean.isStarted();
    }

    /**
     * Simple {@link LifecycleBean} implementation that outputs event type when it is occurred.
     */
    public static class LifecycleExampleBean implements LifecycleBean {
        /** Auto-inject grid instance. */
        @IgniteInstanceResource
        private Ignite ignite;

        /** Started flag. */
        private boolean isStarted;

        /** {@inheritDoc} */
        @Override public void onLifecycleEvent(LifecycleEventType evt) {
            System.out.println();
            System.out.println(">>> Grid lifecycle event occurred: " + evt);
            System.out.println(">>> Grid name: " + ignite.name());

            if (evt == LifecycleEventType.AFTER_NODE_START) {
                isStarted = true;
            }
            else if (evt == LifecycleEventType.AFTER_NODE_STOP) {
                isStarted = false;
            }
        }

        /**
         * @return {@code True} if grid has been started.
         */
        public boolean isStarted() {
            return isStarted;
        }
    }
}

