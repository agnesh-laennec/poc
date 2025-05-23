# Android POC App

## CI/CD Setup with Google Cloud Platform

### Prerequisites
1. Google Cloud Platform account
2. Android Studio
3. Google Cloud SDK installed
4. A keystore file for signing the release APK

### Setup Steps

1. **Enable Required APIs**
   ```bash
   gcloud services enable cloudbuild.googleapis.com
   gcloud services enable storage.googleapis.com
   ```

2. **Create a Cloud Storage Bucket**
   ```bash
   gsutil mb gs://poc-app-releases
   ```

3. **Set up Cloud Build Triggers**
   - Go to Cloud Build > Triggers
   - Create a new trigger
   - Connect your repository
   - Set the following configuration:
     - Event: Push to a branch
     - Source: Your repository
     - Branch: ^main$
     - Build configuration: Cloud Build configuration file (yaml)
     - Cloud Build configuration file location: /cloudbuild.yaml

4. **Set up Secret Manager**
   ```bash
   # Create secrets for signing configuration
   echo -n "your-keystore-password" | gcloud secrets create keystore-password --data-file=-
   echo -n "your-key-password" | gcloud secrets create key-password --data-file=-
   echo -n "your-key-alias" | gcloud secrets create key-alias --data-file=-
   ```

5. **Upload Keystore**
   ```bash
   gsutil cp your-keystore.jks gs://poc-app-releases/keystore.jks
   ```

6. **Grant Permissions**
   ```bash
   # Grant Cloud Build service account access to Secret Manager
   gcloud projects add-iam-policy-binding $PROJECT_ID \
     --member="serviceAccount:$PROJECT_NUMBER@cloudbuild.gserviceaccount.com" \
     --role="roles/secretmanager.secretAccessor"

   # Grant Cloud Build service account access to Cloud Storage
   gcloud projects add-iam-policy-binding $PROJECT_ID \
     --member="serviceAccount:$PROJECT_NUMBER@cloudbuild.gserviceaccount.com" \
     --role="roles/storage.objectViewer"
   ```

### Manual Build
To trigger a build manually:
```bash
gcloud builds submit --config=cloudbuild.yaml
```

### Build Artifacts
- Debug APK: `app/build/outputs/apk/debug/app-debug.apk`
- Release APK: `app/build/outputs/apk/release/app-release.apk`
- Test Results: `app/build/reports/tests/`

### Monitoring
- View build history in Cloud Build console
- Check build logs for detailed information
- Monitor test results in the test reports

### Troubleshooting
1. If build fails, check:
   - Cloud Build logs
   - Gradle build logs
   - Test reports
2. Common issues:
   - Missing permissions
   - Invalid keystore configuration
   - Failed tests
   - Memory issues during build 