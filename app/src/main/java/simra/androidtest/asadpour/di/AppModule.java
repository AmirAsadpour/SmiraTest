package simra.androidtest.asadpour.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;
import simra.androidtest.asadpour.data.remote.WebService;

@Module
@InstallIn(ApplicationComponent.class)
public class AppModule {
    @Provides
    @Singleton
    public static WebService provideWebservice() {
        return WebService.Creator.newWebService();
    }
}
