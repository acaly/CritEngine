package cn.weathfold.demo.game.misc;

import cn.weathfold.critengine.entity.IEntityTemplate;
import cn.weathfold.critengine.scene.Scene;
import cn.weathfold.critengine.sound.CESoundEngine;
import cn.weathfold.critengine.sound.SoundEmitter;
import cn.weathfold.demo.game.SceneGame;
import cn.weathfold.demo.game.player.EntityPlayer;

/**
 * 弹药实体
 * @author WeAthFolD
 */
public class EntityAmmo extends EntityPickable {
	
	public EntityAmmo(Scene scene, double x, double y) {
		super(scene, x, y, SceneGame.TEX_AMMO);
	}

	@Override
	public void onPlayerInteraction(EntityPlayer player) {
		player.ammo += 9;
		CESoundEngine.playSound(SceneGame.SND_AMMO, new SoundEmitter(300,
				(float) getX(), (float) getY()));
	}
	
	public static class Template implements IEntityTemplate {
		public Scene scene;

		public Template(Scene scene) {
			this.scene = scene;
		}

		@Override
		public void generate(double x, double y) {
			this.scene.spawnEntity(new EntityAmmo(this.scene, x, y));
		}
	}
	
}