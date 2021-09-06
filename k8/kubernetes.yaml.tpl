apiVersion: apps/v1
kind: Deployment
metadata:
  namespace: prod
  name: prod-ti-backend
  labels:
    app: java-springboot
spec:
  replicas: 1
  selector:
    matchLabels:
      app: java-springboot
  strategy:
    rollingUpdate:
      maxSurge: 1
      maxUnavailable: 1
    type: RollingUpdate
  template:
    metadata:
      labels:
        app: java-springboot
    spec:
      containers:
        - name: prod-ti-backend
          image: gcr.io/GOOGLE_CLOUD_PROJECT/spring-boot:TAG_NAME
          imagePullPolicy: Always
          envFrom:
          - secretRef:
             name: prod-ti-backend-secret
          - configMapRef:
             name: prod-ti-backend-configmap
          env:
          - name: GCP_CREDENTIAL_JSON_FILE
            value: /var/secrets/google/gcp-sa-key.json
         # - name: FIREBASE_CONFIG_JSON_FILE
         #   value: /var/secrets/google/firebase-sa-key.json
          ports:
            - containerPort: 8081
          volumeMounts:
          - name: gcp-sa-key
            mountPath: /var/secrets/google
        #  volumeMounts:
        #  - name: firebase-sa-key
        #    mountPath: /var/secrets/google
          resources:
             requests:
               memory: "500Mi"
               cpu: "500m"
             limits:
                memory: "800Mi"
                cpu: "1700m"   
          readinessProbe:
            httpGet:
              path: /
              port: 8081
            initialDelaySeconds: 30
            timeoutSeconds: 10
            periodSeconds: 10
      volumes:
      - name: gcp-sa-key
        secret:
          secretName: prod-ti-backend-gcp-sa-secret
     # volumes:
     # - name: firebase-sa-key
     #   secret:
     #     secretName: prod-ti-backend-firebase-sa-secret
