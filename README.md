# Usage examples
### Adding a Task
1. Open the app.
2. Type a task name in the input field.
3. Press the **"Add"** button.
4. The task will appear in the list.

### Editing a Task
1. Tap on an existing task edit button.
2. Modify the task name.
3. Press the **"Save"** button.
4. The task will be updated in the list.

### Deleting a Task
1. Find the task in the list.
2. Press the **"Remove"** button next to it.
3. The task will be deleted from the list.

# How to Run
## 1. Clone the Repository
1. Open **Android Studio**.
2. Click on **"Get from VCS"** (on the welcome screen).
3. In the **Version Control** dropdown, select **Git**.
4. Paste the repository URL:
   ```
   https://github.com/yourusername/your-repository.git
   ```
5. Click **Clone**.
6. Android Studio will open the project automatically.

### 2. Open the Project in Android Studio
- When prompted, click **Trust** if Android Studio asks whether to trust the project.
- If you see "Unregistered Git Root", go to **VCS → Enable Version Control** → Select **Git**.

### 3. Sync and Build the Project
1. **Sync Dependencies:**
   - Go to **File → Sync Project with Gradle Files**.
2. **Resolve Gradle issues (if any):**
   - If prompted, update Gradle or Kotlin dependencies.
3. **Run a Gradle Build:**
   - Open the terminal inside Android Studio and run:
   - On Windows:
     ```sh
     gradlew build
     ```
4. If everything is fine, you’ll see **"BUILD SUCCESSFUL"**.

### 4️. Run the App
1. Connect an **Android device** via USB **or** start an **emulator**.
2. Click **Run ▶ (Shift + F10)** in Android Studio.
3. Select the device/emulator.
4. The app should now launch! 🚀
