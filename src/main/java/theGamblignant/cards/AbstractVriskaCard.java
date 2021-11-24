package theGamblignant.cards;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theGamblignant.VriskaMod;
import theGamblignant.powers.*;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;

public abstract class AbstractVriskaCard extends CustomCard {

        public static final Logger logger = LogManager.getLogger(VriskaMod.class.getName()); //this is for testing purposes, you can remove this eventually

    public AbstractVriskaCard(final String id,
                              final String img,
                              final int cost,
                              final CardType type,
                              final CardColor color,
                              final CardRarity rarity,
                              final CardTarget target) {

        super(id, languagePack.getCardStrings(id).NAME, img, cost, languagePack.getCardStrings(id).DESCRIPTION, type, color, rarity, target);

        // Set all the things to their default values.
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
    }

    public void displayUpgrades() { // Display the upgrade - when you click a card to upgrade it
        super.displayUpgrades();
    }

    public int roll(int faces, char purpose) {
        //for purpose, 'a' = attack, 's' = skill, 'o' = other (used for wisdom/charisma)
        int luckAmt = 0;
        int result;
        int max;
        int min;

        if (AbstractDungeon.player.hasPower(LuckPower.POWER_ID)) {
            luckAmt += AbstractDungeon.player.getPower(LuckPower.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasPower(VimPower.POWER_ID)) {
            luckAmt += AbstractDungeon.player.getPower(VimPower.POWER_ID).amount;
            AbstractDungeon.player.getPower(VimPower.POWER_ID).flash();
            this.addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, VimPower.POWER_ID));
        }
        if (AbstractDungeon.player.hasPower(CharismaPower.POWER_ID)&&purpose=='a') {
            luckAmt += AbstractDungeon.player.getPower(CharismaPower.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasPower(WisdomPower.POWER_ID)&&purpose=='s') {
            luckAmt += AbstractDungeon.player.getPower(WisdomPower.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasPower(LoadedDicePower.POWER_ID)) {
            this.addToBot(new DamageRandomEnemyAction(new DamageInfo(AbstractDungeon.player, AbstractDungeon.player.getPower(LoadedDicePower.POWER_ID).amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }

        logger.info("luck going into this roll: "+luckAmt); //you can remove this eventually

        if (luckAmt < 0) {          //if your luck is negative,
            max = faces + luckAmt;  //the highest roll is the amount of faces minus your bad luck,
            min = 1;                //and the lowest roll is 1.
        } else {                    //otherwise, if your luck is positive or 0,
            max = faces;            //the highest roll is the amount of faces,
            min = 1 + luckAmt;      //and the lowest roll is 1 plus your luck.
        }

        logger.info("rolling from "+min+" to "+max);

        if (max <= 1) {result = 1;}                 //if your maximum roll is lower than 1, just return 1
        else if (max <= min) {result = max;}        //if your min is greater or equal to your max, return the max
        else {result = AbstractDungeon.cardRandomRng.random(min, max);} //otherwise, just do the fricken roll

        if ((result == 1) && (AbstractDungeon.player.hasPower(SuperstitionPower.POWER_ID))) {
            this.addToTop(new DamageAllEnemiesAction((AbstractCreature)null, DamageInfo.createDamageMatrix(AbstractDungeon.player.getPower(SuperstitionPower.POWER_ID).amount, true), DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.POISON));
        }

        logger.info("rolled a "+result);
        return result;
    }
}