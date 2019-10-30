package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import static com.jme3.animation.LoopMode.DontLoop;
import static com.jme3.animation.LoopMode.Loop;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import static com.jme3.math.FastMath.PI;
import com.jme3.math.Vector3f;
import com.jme3.post.FilterPostProcessor;
import com.jme3.post.filters.FogFilter;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.util.TangentBinormalGenerator;

/**
 * @author 3D model by Artur Bardowski
 */
public class Main extends SimpleApplication {

    public static void main(String[] args) {
        Main app = new Main();
        app.start();

    }
    private Node object3dNode;
    private Spatial modelMesh;
    private RigidBodyControl physicsObject3d;
    private Node floorNode;
    private Spatial modelBackdrop;
    private FilterPostProcessor fpp;
    private AnimControl control1;
    private AnimChannel channel1;

    @Override
    public void simpleInitApp() {

// See below
// OBJECT 3D ---
        object3dNode = new Node("supply_crate");
        modelMesh = assetManager.loadModel("Models/spiderex1/spiderex1.j3o");
        TangentBinormalGenerator.generate(modelMesh);
        physicsObject3d = new RigidBodyControl(0f);
        object3dNode.setLocalTranslation(new Vector3f(0, -2.2f, 1));
        object3dNode.addControl(physicsObject3d);
        object3dNode.attachChild(modelMesh);
        rootNode.attachChild(object3dNode);

        control1 = object3dNode.getChild("spideRex.head").getControl(AnimControl.class);
        control1.getAnimationNames();

        //  Shadow
        Spatial shadow = assetManager.loadModel("Models/cien/cien.j3o");
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setTexture("ColorMap", assetManager.loadTexture("Models/cien/cien.png"));

        mat.setFloat("AlphaDiscardThreshold", 0.1f);
        shadow.setQueueBucket(RenderQueue.Bucket.Transparent);
        mat.getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
        shadow.setMaterial(mat);
        shadow.setLocalScale(4f, 1.6f, 4f);
        object3dNode.attachChild(shadow);
// Floor
        floorNode = new Node("floor");
        modelBackdrop = assetManager.loadModel("Models/backdrop/backdrop.j3o");
        floorNode.setLocalTranslation(new Vector3f(0, -2.21f, 9));
        floorNode.addControl(physicsObject3d);
        floorNode.attachChild(modelBackdrop);
        rootNode.attachChild(floorNode);

        //FOG
        fpp = new FilterPostProcessor(assetManager);
        FogFilter fog = new FogFilter();
        fog.setFogColor(new ColorRGBA(0.9f, 0.9f, 0.8f, 1.0f));

        fog.setFogDistance(30);
        fog.setFogDensity(2f);
        fpp.addFilter(fog);
        viewPort.addProcessor(fpp);

// Light     
        DirectionalLight sun = new DirectionalLight();
        sun.setColor(ColorRGBA.White);
        sun.setDirection(new Vector3f(-3f, -3f, -3));
        rootNode.addLight(sun);

    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code

        control1.getAnim("Idle");  // Idle, walking , atack, die
        channel1 = control1.createChannel();
        channel1.setAnim("Idle"); // Idle, walking , atack, die
       // channel1.setSpeed(0.5f);
        channel1.setLoopMode(Loop);
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
