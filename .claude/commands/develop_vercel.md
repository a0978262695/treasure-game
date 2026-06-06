Deploy the current project's frontend to Vercel via the Vercel web dashboard (not CLI).

This command sets up everything needed so the user can import the project in the Vercel web UI.

## Steps

### 1. Prepare the build
Run `npm run build` to produce the `build/` output directory.

### 2. Initialize Git repository (if not already)
```bash
git init
git add .
git commit -m "Initial commit"
```

### 3. Push to GitHub
- Install GitHub CLI if not present: `brew install gh`
- Authenticate: `gh auth login`
- Create a new public/private repo and push:
  ```bash
  gh repo create <project-name> --public --source=. --remote=origin --push
  ```
- If gh is unavailable, ask the user to create a GitHub repo manually and provide the remote URL, then:
  ```bash
  git remote add origin <url>
  git push -u origin main
  ```

### 4. Import on Vercel web dashboard
Direct the user to:
1. Go to https://vercel.com/new
2. Click "Import Git Repository" and select the GitHub repo
3. Set these build settings:
   - **Framework Preset**: Other
   - **Build Command**: `npm run build`
   - **Output Directory**: `build`
4. Click "Deploy"
5. After deployment, copy the production URL (e.g. `https://<project>.vercel.app`) and report it back

### Vercel project settings (vercel.json)
Ensure `vercel.json` exists at the project root:
```json
{
  "buildCommand": "npm run build",
  "outputDirectory": "build",
  "framework": null
}
```

### .vercelignore
Ensure `.vercelignore` excludes:
```
backend/
node_modules/
*.db
```

## Notes
- Only the **frontend** (Vite static build) is deployed. The Spring Boot backend is not included.
- The backend API calls in the frontend point to `localhost:8080` — after Vercel deployment, you'll need to deploy the backend separately and update the API base URL in `src/lib/api.ts`.
