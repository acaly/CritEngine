package cn.weathfold.demo.game.misc;

import cn.weathfold.critengine.entity.Entity;
import cn.weathfold.critengine.entity.IEntityFilter;
import cn.weathfold.demo.game.player.EntityPlayer;

/**
 * 只通过玩家实体的滤镜
 * @author WeAthFolD
 */
public class PlayerFilter implements IEntityFilter {
	
	public static IEntityFilter INSTANCE = new PlayerFilter();

	@Override
	public boolean isEntityApplicable(Entity ent) {
		return ent instanceof EntityPlayer;
	}
	
}