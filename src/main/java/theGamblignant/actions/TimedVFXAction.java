package theGamblignant.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

//this is copied from Downfall's Bronze Automaton

public class TimedVFXAction extends AbstractGameAction {
  private AbstractGameEffect effect;
  
  private boolean added = false;
  
  public TimedVFXAction(AbstractGameEffect effect) {
    this.effect = effect;
  }
  
  public void update() {
    if (!this.added) {
      AbstractDungeon.effectList.add(this.effect);
      this.added = true;
    } 
    this.isDone = this.effect.isDone;
  }
}
