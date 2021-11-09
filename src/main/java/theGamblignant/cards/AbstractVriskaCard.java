package theGamblignant.cards;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import theGamblignant.VriskaMod;
import theGamblignant.powers.LuckPower;
import theGamblignant.powers.VimPower;
import theGamblignant.relics.TetradactylyRelic;

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

    public int roll(int faces) {
        int luckAmt = 0;
        int result;
        int max;
        int min;

        if (AbstractDungeon.player.hasRelic(TetradactylyRelic.ID)) {
            AbstractDungeon.player.getRelic(TetradactylyRelic.ID).onTrigger();
        }

        if (AbstractDungeon.player.hasPower(LuckPower.POWER_ID)) {
            luckAmt += AbstractDungeon.player.getPower(LuckPower.POWER_ID).amount;
        }
        if (AbstractDungeon.player.hasPower(VimPower.POWER_ID)) {
            luckAmt += AbstractDungeon.player.getPower(VimPower.POWER_ID).amount;
            AbstractDungeon.player.getPower(VimPower.POWER_ID).flash();
            this.addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, VimPower.POWER_ID));
        }

        logger.info("luck going into this roll: "+luckAmt); //you can remove this eventually

        if (luckAmt < 0) {         //if your luck is negative,
            max = faces + luckAmt; //the highest roll is the amount of faces minus your bad luck,
            min = 1;               //and the lowest roll is 1.
        } else {               //otherwise, if your luck is positive or 0,
            max = faces;       //the highest roll is the amount of faces,
            min = 1 + luckAmt; //and the lowest roll is 1 plus your luck.
        }

        if (max <= 1) {result = 1;} //if your maximum roll is lower than 1, just return 1
        else if (max <= min) {result = max;} //if your min is greater or equal to your max, return the max
        else {result = AbstractDungeon.cardRandomRng.random(min, max);} //do the fricken roll

        logger.info("rolled a "+result);
        return result;
    }
}